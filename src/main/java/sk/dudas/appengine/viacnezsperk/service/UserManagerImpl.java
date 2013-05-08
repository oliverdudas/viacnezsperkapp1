package sk.dudas.appengine.viacnezsperk.service;

import com.google.appengine.api.datastore.Key;
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

    private static final String USERS = "USERS";

    @Autowired
    @Qualifier("userDao")
    private UserDao dao;

    @PostConstruct
    public final void init() {
        super.setBaseDao(dao);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void persistOrMergeNonTransactionalUser(User user) {
        Date date = new Date();
        String fullname = MainUtil.getLoggedUser().getFullname();
        if (user.getKey() == null) {
            user.setCreated(date);
            user.setCreatedBy(fullname);
            user.setModified(date);
            user.setModifiedBy(fullname);
            addDefaultRole(user);
            persistNonTransactional(user);
        } else {
            user.setModified(date);
            user.setModifiedBy(fullname);
            mergeNonTransactional(user);
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
