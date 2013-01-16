package sk.dudas.appengine.viacnezsperk.service;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.MediaStreamSource;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;
import org.gmr.web.multipart.GMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sk.dudas.appengine.viacnezsperk.dao.UserDao;
import sk.dudas.appengine.viacnezsperk.domain.Role;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.util.MainUtil;
import sk.dudas.appengine.viacnezsperk.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 15.10.2012
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
@Service("userManager")
public class UserManagerImpl extends BaseManagerImpl<Key, User> implements UserManager {

    /**
     * oliver.dudas@viacnezsperk.sk google user id
     */
    private static final String GOOGLE_USER_ID = "104004273393078620402";
    private static final String GOOGLE_USER_EMAIL = "oliver.dudas@viacnezsperk.sk";
    private static final String GOOGLE_USER_EMAIL_PASSWORD = "sperk123";

    /**
     * viacnezsperk album id on oliver.dudas@viacnezsperk.sk google user
     */
    private static final String GOOGLE_USER_PICASA_ALBUM_ID = "5804367311941076033";
    private static final String USERS = "USERS";

    @Autowired
    @Qualifier("userDao")
    private UserDao dao;

    @PostConstruct
    public final void init() {
        super.setBaseDao(dao);
    }

    public PhotoEntry uploadPhotoToPicasa(GMultipartFile file) throws IOException, ServiceException {
        URL albumPostUrl = new URL("https://picasaweb.google.com/data/feed/api/user/" + GOOGLE_USER_ID + "/albumid/" + GOOGLE_USER_PICASA_ALBUM_ID);

        PhotoEntry myPhoto = new PhotoEntry();
        myPhoto.setTitle(new PlainTextConstruct(file.getOriginalFilename()));
        myPhoto.setDescription(new PlainTextConstruct(file.getOriginalFilename()));
//        myPhoto.setClient("myClientName");

        MediaStreamSource myMedia = new MediaStreamSource(file.getInputStream(), "image/jpeg");
        myPhoto.setMediaSource(myMedia);

        PicasawebService myService = new PicasawebService("viacnezsperk");
        myService.setReadTimeout(1000000);
        myService.setConnectTimeout(1000000);
        myService.setUserCredentials(GOOGLE_USER_EMAIL, GOOGLE_USER_EMAIL_PASSWORD);

        return myService.insert(albumPostUrl, myPhoto);
    }

    public void persistOrMergeUser(User user) {
        Date date = new Date();
        String fullname = MainUtil.getLoggedUser().getFullname();
        if (user.getKey() == null) {
            user.setCreated(date);
            user.setCreatedBy(fullname);
            user.setModified(date);
            user.setModifiedBy(fullname);
            addDefaultRole(user);
            persist(user);
        } else {
            user.setModified(date);
            user.setModifiedBy(fullname);
            merge(user);
        }
        updateUsersInCache(user);
    }

    @Override
    public void removeUser(Key key) {
        remove(key);
        removeUserFromCache(key);
    }

    private void removeUserFromCache(Key key) {
        List<User> unattachedUsers = findAllUnattachedUsers();
        for (User unattachedUser : unattachedUsers) {
            if (unattachedUser.getKey().getId() == key.getId()) {
                unattachedUsers.remove(unattachedUser);
                putObjectToCache(USERS, unattachedUsers);
                break;
            }
        }
    }

    private void updateUsersInCache(User user) {
        List<User> unattachedUsers = findAllUnattachedUsers();
        if (user.getKey() != null) {
            for (User unattachedUser : unattachedUsers) {
                if (unattachedUser.getKey().getId() == user.getKey().getId()) {
                    unattachedUsers.remove(unattachedUser);
                    unattachedUsers.add(user);
                    putObjectToCache(USERS, unattachedUsers);
                    break;
                }
            }
        } else {
            unattachedUsers.add(user);
            putObjectToCache(USERS, unattachedUsers);
        }
    }

    private void addDefaultRole(User child) {
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(new Role(Role.ROLE_USER));
        child.setRoles(roles);
    }

    /**
     * This creation of new list is required beacause of directly returned unserializable list from datastore.
     * The returned list is NOT SERIALIZABLE, thus, we cannot put the returned list to the appengine's session.
     * To make the returned list serializable, we have to create new one.
     *
     * @return
     */
    public List<User> findAllUnattachedUsers() {
        List<User> users = getObjectFromCache(USERS);
        if (users != null) {
            return users;
        } else {
            users = new ArrayList<User>(findAll());
            putObjectToCache(USERS, users);
        }
        return users;
    }

    public List<User> getUsers(String searchValue) {
        List<User> allUsers = findAllUnattachedUsers();
        if (searchValue != null && !searchValue.isEmpty()) {
            List<User> predicateUsers = new ArrayList<User>();
            for (User user : allUsers) {
                String allNames = StringUtils.normalize(user.getAllNames());
                String search = StringUtils.normalize(searchValue);
                if (allNames.contains(search)) {
                    predicateUsers.add(user);
                }
            }
            return predicateUsers;
        } else {
            return allUsers;
        }
    }

}
