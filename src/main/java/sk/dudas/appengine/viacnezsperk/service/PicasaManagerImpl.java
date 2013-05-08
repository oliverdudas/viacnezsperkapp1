package sk.dudas.appengine.viacnezsperk.service;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.PubControl;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.media.MediaStreamSource;
import com.google.gdata.data.photos.*;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.ServiceForbiddenException;
import org.gmr.web.multipart.GMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 15.10.2012
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
@Service("picasaManager")
public class PicasaManagerImpl implements PicasaManager {

    /**
     * oliver.dudas@viacnezsperk.sk google user id
     */
    private static final String GOOGLE_USER_ID = "104004273393078620402";
    private static final String GOOGLE_USER_EMAIL = "oliver.dudas@viacnezsperk.sk";
    private static final String GOOGLE_USER_EMAIL_PASSWORD = "sperk123";

    /**
     * viacnezsperk album id on oliver.dudas@viacnezsperk.sk google user
     */
//    private static final String GOOGLE_USER_PICASA_ALBUM_ID = "5804367311941076033";
//    private static final String PHOTO_LIMIT_REACHED = "Photo limit reached.\n";

    private static final String KIND_ALBUM = "?kind=album&v=2.0";
    private static final String VIACNEZSPERK = "viacnezsperk";

    public PhotoEntry uploadPhotoToPicasa(GMultipartFile file) throws IOException, ServiceException {
        URL albumPostUrl = getAlbumUrl(getAlbumIdWithFreeSpaceLeft());

        PhotoEntry myPhoto = new PhotoEntry();
        myPhoto.setTitle(new PlainTextConstruct(file.getOriginalFilename()));
        myPhoto.setDescription(new PlainTextConstruct(file.getOriginalFilename()));
//        myPhoto.setClient("myClientName");

        MediaStreamSource myMedia = new MediaStreamSource(file.getInputStream(), "image/jpeg");
        myPhoto.setMediaSource(myMedia);

        PicasawebService myService = createPicasawebService();
        return myService.insert(albumPostUrl, myPhoto);
//        try {
//        } catch (ServiceForbiddenException e) {
//            if (PHOTO_LIMIT_REACHED.equals(e.getResponseBody())) {
//                String s = "";
//                //todo: create new album and upload the photo to it
//            }
//            throw new RuntimeException(e);
//        }
    }

    private PicasawebService createPicasawebService() throws AuthenticationException {
        PicasawebService myService = new PicasawebService(VIACNEZSPERK);
        myService.setReadTimeout(1000000);
        myService.setConnectTimeout(1000000);
        myService.setUserCredentials(GOOGLE_USER_EMAIL, GOOGLE_USER_EMAIL_PASSWORD);
        return myService;
    }

    private URL getAlbumUrl(String albumId) throws MalformedURLException {
        return new URL(getUserFeedLink() + "/albumid/" + albumId);
    }

    private String getUserFeedLink() {
        return "https://picasaweb.google.com/data/feed/api/user/" + GOOGLE_USER_ID;
    }

    public String getAlbumIdWithFreeSpaceLeft() throws IOException, ServiceException {
        PicasawebService myService = createPicasawebService();
        UserFeed userAlbumFeed = getUserAlbumFeed(myService);
        List<GphotoEntry> entries = userAlbumFeed.getEntries();
        for (GphotoEntry entry : entries) {
            Integer photosLeftCount = entry.getExtension(GphotoPhotosLeft.class).getValue();
            if (photosLeftCount > 0) {
                return entry.getGphotoId();
            }
        }
        int postfix = entries.size() + 1;
        return addNewAlbum(myService, VIACNEZSPERK + postfix);
    }

    private UserFeed getUserAlbumFeed(PicasawebService myService) throws IOException, ServiceException {
        return myService.getFeed(new URL(getUserFeedLink() + KIND_ALBUM), UserFeed.class);
    }

    private String addNewAlbum(PicasawebService myService, String newTitle) throws IOException, ServiceException {
        AlbumEntry albumEntry = new AlbumEntry();

        albumEntry.setTitle(new PlainTextConstruct(newTitle));
        albumEntry.setDescription(new PlainTextConstruct("Viacnezsperk"));
        GphotoAccess accessExt = new GphotoAccess(GphotoAccess.Value.PUBLIC);
        albumEntry.setAccessExt(accessExt);

        AlbumEntry insertedEntry = myService.insert(new URL(getUserFeedLink()), albumEntry);
        return insertedEntry.getTitle().getPlainText();
    }

}
