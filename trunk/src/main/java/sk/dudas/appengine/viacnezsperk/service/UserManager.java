package sk.dudas.appengine.viacnezsperk.service;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;
import org.gmr.web.multipart.GMultipartFile;
import sk.dudas.appengine.viacnezsperk.domain.User;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 15.10.2012
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public interface UserManager extends BaseManager<Key, User> {

    PhotoEntry uploadPhotoToPicasa(GMultipartFile file) throws IOException, ServiceException;

    void persistOrMergeUser(User user);

    List<User> findAllUnattachedUsers();

    List<User> getUsers(String searchValue);
}
