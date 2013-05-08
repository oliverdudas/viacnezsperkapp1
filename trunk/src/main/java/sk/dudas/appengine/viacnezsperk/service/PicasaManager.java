package sk.dudas.appengine.viacnezsperk.service;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;
import org.gmr.web.multipart.GMultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 15.10.2012
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public interface PicasaManager {

    PhotoEntry uploadPhotoToPicasa(GMultipartFile file) throws IOException, ServiceException;

}
