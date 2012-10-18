package sk.dudas.appengine.viacnezsperk;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import sk.dudas.appengine.viacnezsperk.domain.Role;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.service.CustomUserDetailService;
import sk.dudas.appengine.viacnezsperk.service.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 19.10.2012
 * Time: 19:46
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractSecurityTest extends BaseTest {

    private static final String ADMIN = "admin";
    @Autowired
    @Qualifier(value = "authenticationManager")
    private AuthenticationManager am;

    @Autowired
    @Qualifier(value = "userManager")
    protected UserManager userManager;

    @Before
    public void setup() {
        initUsers();
        login(ADMIN, ADMIN);
    }

    @After
    public void clear() {
        SecurityContextHolder.clearContext();
    }

    protected void login(String name, String password) {
        Authentication auth = new UsernamePasswordAuthenticationToken(name, password);
        SecurityContextHolder.getContext().setAuthentication(am.authenticate(auth));
    }

    protected void addUser(String namePass, String... roleNames) {
        User user = new User();
        PasswordEncoder encoder = new Md5PasswordEncoder();
        String hashedPass = encoder.encodePassword(namePass, null);
        user.setPassword(hashedPass);
        user.setUsername(namePass);
        ArrayList<Role> roles = new ArrayList<Role>();
        for (String roleName : roleNames) {
            roles.add(new Role(roleName));
        }
        user.setRoles(roles);
        userManager.persistEntity(user);
    }

    private void initUsers() {
        List<User> all = userManager.findAll();
        if (all == null || all.size() == 0) {
            addUser(ADMIN, Role.ROLE_ADMIN, Role.ROLE_USER);
            addUser("fero", Role.ROLE_USER);
            addUser("jozo", Role.ROLE_USER);
            addUser("dezi", Role.ROLE_USER);
            addUser("pepi", Role.ROLE_USER);
        }
    }

}
