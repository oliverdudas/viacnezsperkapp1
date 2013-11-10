package sk.dudas.appengine.viacnezsperk.service;

import com.google.appengine.api.datastore.Key;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.domain.UserBrowse;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 10.11.2013
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
public interface UserBrowseManager extends BaseManager<Key, UserBrowse> {

    List<UserBrowse> getUsers(String searchValue);

    List<UserBrowse> findAllUnattachedUsers();

    void removeUserFromCache(Key key);

    void updateUsersInCache(User key);
}
