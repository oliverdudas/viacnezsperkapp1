package sk.dudas.appengine.viacnezsperk.service;

import com.google.appengine.api.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sk.dudas.appengine.viacnezsperk.dao.UserBrowseDao;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.domain.UserBrowse;
import sk.dudas.appengine.viacnezsperk.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 10.11.2013
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
@Service("userBrowseManager")
public class UserBrowseManagerImpl extends BaseManagerImpl<Key, UserBrowse> implements UserBrowseManager {

    private static final String USERS = "USERS";

    @Autowired
    @Qualifier("userBrowseDao")
    private UserBrowseDao dao;

    @PostConstruct
    public final void init() {
        super.setBaseDao(dao);
    }

    /**
     * This creation of new list is required beacause of directly returned unserializable list from datastore.
     * The returned list is NOT SERIALIZABLE, thus, we cannot put the returned list to the appengine's session.
     * To make the returned list serializable, we have to create new one.
     *
     * @return
     */
    public List<UserBrowse> findAllUnattachedUsers() {
        List<UserBrowse> users = getObjectFromCache(USERS);
        if (users != null) {
            return users;
        } else {
            users = new ArrayList<UserBrowse>(findAll());
            putObjectToCache(USERS, users);
        }
        return users;
    }

    public void removeUserFromCache(Key key) {
        List<UserBrowse> unattachedUsers = findAllUnattachedUsers();
        for (UserBrowse unattachedUser : unattachedUsers) {
            if (unattachedUser.getKey().getId() == key.getId()) {
                unattachedUsers.remove(unattachedUser);
                putObjectToCache(USERS, unattachedUsers);
                break;
            }
        }
    }

    public void updateUsersInCache(User user) {
        UserBrowse userBrowse = createUserBrowse(user);
        List<UserBrowse> unattachedUsers = findAllUnattachedUsers();
        if (userBrowse.getKey() != null) {
            for (UserBrowse unattachedUser : unattachedUsers) {
                if (unattachedUser.getKey().getId() == userBrowse.getKey().getId()) {
                    unattachedUsers.remove(unattachedUser);
                    unattachedUsers.add(userBrowse);
                    putObjectToCache(USERS, unattachedUsers);
                    break;
                }
            }
        } else {
            unattachedUsers.add(userBrowse);
            putObjectToCache(USERS, unattachedUsers);
        }
    }

    private UserBrowse createUserBrowse(User user) {
        UserBrowse userBrowse = new UserBrowse();
        userBrowse.setKey(user.getKey());
        userBrowse.setVersion(user.getVersion());
        userBrowse.setUsername(user.getUsername());
        userBrowse.setFirstname(user.getFirstname());
        userBrowse.setLastname(user.getLastname());
        userBrowse.setCreated(user.getCreated());
        userBrowse.setModified(user.getModified());
        return userBrowse;
    }

    public List<UserBrowse> getUsers(String searchValue) {
        List<UserBrowse> allUsers = findAllUnattachedUsers();
        if (searchValue != null && !searchValue.isEmpty()) {
            List<UserBrowse> predicateUsers = new ArrayList<UserBrowse>();
            for (UserBrowse user : allUsers) {
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
