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

    @Autowired
    @Qualifier("userDao")
    private UserDao dao;

    @Autowired
    @Qualifier("userBrowseManager")
    private UserBrowseManager userBrowseManager;

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
        userBrowseManager.updateUsersInCache(user);
    }

    @Override
    public void removeUser(Key key) {
        remove(key);
        userBrowseManager.removeUserFromCache(key);
    }

    private void addDefaultRole(User child) {
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(new Role(Role.ROLE_USER));
        child.setRoles(roles);
    }

//    public List<User> getUsers(String searchValue) {
//        List<User> allUsers = findAllUnattachedUsers();
//        if (searchValue != null && !searchValue.isEmpty()) {
//            List<User> predicateUsers = new ArrayList<User>();
//            for (User user : allUsers) {
//                String allNames = StringUtils.normalize(user.getAllNames());
//                String search = StringUtils.normalize(searchValue);
//                if (allNames.contains(search)) {
//                    predicateUsers.add(user);
//                }
//            }
//            return predicateUsers;
//        } else {
//            return allUsers;
//        }
//    }

    public User findUserById(long id) {
        User byId = findById(id);
        byId.getContent(); // fetching lazy field
        byId.getGalleryItems(); // fetching lazy field
        return byId;
    }
}
