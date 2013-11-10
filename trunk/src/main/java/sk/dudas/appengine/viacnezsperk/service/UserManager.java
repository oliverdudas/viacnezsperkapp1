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

    void persistOrMergeNonTransactionalUser(User user);

    User findUserById(long id);

    void removeUser(Key key);
}
