package sk.dudas.appengine.viacnezsperk.service;

import com.google.appengine.api.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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

    @PostConstruct
    public final void init() {
        super.setBaseDao(dao);
        initAdminUser();
    }

    private void initAdminUser() {

        UserDetails admin = loadUserByUsername(ADMIN);
        if (admin == null) {

//            Role roleUser = initRole(Role.ROLE_USER);
            Role roleUser = new Role(Role.ROLE_USER);
//            Role roleAdmin = initRole(Role.ROLE_ADMIN);
            Role roleAdmin = new Role(Role.ROLE_ADMIN);

            User user = new User();
            PasswordEncoder encoder = new Md5PasswordEncoder();
            String hashedPass = encoder.encodePassword(ADMIN, null);
            user.setPassword(hashedPass);
            user.setUsername(ADMIN);
            ArrayList<Role> roles = new ArrayList<Role>();
            roles.add(roleUser);
            roles.add(roleAdmin);
            user.setRoles(roles);
            persistEntity(user);
        }
    }

    private Role initRole(String rolename) {
        Role roleUser = dao.loadRoleByRolename(rolename);
        if (roleUser == null) {
            roleUser = new Role(rolename);
            persistEntity(roleUser);
        }
        return roleUser;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dao.loadUserByUsername(username);
    }
}
