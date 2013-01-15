package sk.dudas.appengine.viacnezsperk.service;

import com.google.appengine.api.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sk.dudas.appengine.viacnezsperk.dao.UserDetailDao;
import sk.dudas.appengine.viacnezsperk.domain.Role;
import sk.dudas.appengine.viacnezsperk.domain.User;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 15.10.2012
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("customUserDetailService")
public class CustomUserDetailService extends BaseManagerImpl<Key, User> implements UserDetailsService {

    private static final String ADMIN = "admin";
    @Autowired
    @Qualifier("customUserDetailDao")
    private UserDetailDao dao;

    @Autowired
    @Qualifier("userManager")
    private UserManager userManager;

//    @Autowired
//    private Environment environment;

    @PostConstruct
    public final void init() {
        super.setBaseDao(dao);
//        String[] activeProfiles = environment.getActiveProfiles();
//        if (activeProfiles.length == 1 && activeProfiles[0].equals("test")) {
//            //todo: in the test phase we dont call initUsers()
//        } else {
//            initUsers();
//        }
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dao.loadUserByUsername(username);
    }

    protected void addUser(String namePass, String... roleNames) {
        User user = new User();
        user.setCreated(new Date());
        user.setModified(new Date());
        user.setCreatedBy("Application");
        user.setModifiedBy("Application");
//        PasswordEncoder encoder = new Md5PasswordEncoder();
//        String hashedPass = encoder.encodePassword(namePass, null);
//        user.setPassword(hashedPass);
        user.setPassword(namePass);
        user.setUsername(namePass);
        ArrayList<Role> roles = new ArrayList<Role>();
        for (String roleName : roleNames) {
            roles.add(new Role(roleName));
        }
        user.setRoles(roles);
        userManager.persistEntity(user);
    }

    private void initUsers() {
        //todo: !!! sposobuje vycerpanie kvoty(Datastore Read Operations) pri kazdom nacitani contextu
//        List<User> all = userManager.findAll();
//        if (all == null || all.size() == 0) {
//            addUser(ADMIN, Role.ROLE_ADMIN, Role.ROLE_USER);
//            addUser("fero", Role.ROLE_USER);
//            addUser("jozo", Role.ROLE_USER);
//            addUser("dezi", Role.ROLE_USER);
//            addUser("pepi", Role.ROLE_USER);
//        }
    }
}
