package sk.dudas.appengine.viacnezsperk.service;

import com.google.appengine.api.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sk.dudas.appengine.viacnezsperk.dao.UserDao;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.util.MainUtil;

import javax.annotation.PostConstruct;
import java.util.Date;

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

    @PostConstruct
    public final void init() {
        super.setBaseDao(dao);
    }

    public void persistOrMergeUser(User user) {
        Date date = new Date();
        String fullname = MainUtil.getLoggedUser().getFullname();
        if (user.getKey() == null) {
            user.setCreated(date);
            user.setCreatedBy(fullname);
            user.setModified(date);
            user.setModifiedBy(fullname);
            persist(user);
        } else {
            user.setModified(date);
            user.setModifiedBy(fullname);
            merge(user);
        }
    }

}
