package sk.dudas.appengine.viacnezsperk.service;

import com.google.appengine.api.datastore.Key;
import sk.dudas.appengine.viacnezsperk.domain.User;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 15.10.2012
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public interface UserManager extends BaseManager<Key, User> {

    void persistOrMergeUser(User user);
}
