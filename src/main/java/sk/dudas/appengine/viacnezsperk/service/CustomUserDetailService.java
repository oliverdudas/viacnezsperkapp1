package sk.dudas.appengine.viacnezsperk.service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.utils.SystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sk.dudas.appengine.viacnezsperk.dao.UserDetailDao;
import sk.dudas.appengine.viacnezsperk.domain.GalleryItem;
import sk.dudas.appengine.viacnezsperk.domain.Role;
import sk.dudas.appengine.viacnezsperk.domain.User;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
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
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
            initUsers();
        }
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
        user.setContent(new Text("aaaaaaaaaaaa"));
        user.setGalleryItems(getTestGalleryItems());
        userManager.persistEntity(user);
    }

    private Collection<GalleryItem> getTestGalleryItems() {
        ArrayList<GalleryItem> galleryItems = new ArrayList<GalleryItem>();
        galleryItems.add(new GalleryItem("1", "url", "url2"));
        galleryItems.add(new GalleryItem("2", "url3", "url4"));
        return galleryItems;
    }

    private void initUsers() {
        //todo: !!! sposobuje vycerpanie kvoty(Datastore Read Operations) pri kazdom nacitani contextu
        List<User> all = userManager.findAll();
        if (all == null || all.size() == 0) {
            addUser(ADMIN, Role.ROLE_ADMIN, Role.ROLE_USER);
            addUser("fero", Role.ROLE_USER);
            addUser("jozo", Role.ROLE_USER);
            addUser("dezi", Role.ROLE_USER);
            addUser("pepi", Role.ROLE_USER);
            addUser("deny", Role.ROLE_USER);
            addUser("beny", Role.ROLE_USER);
            addUser("tuky", Role.ROLE_USER);
            addUser("fuky", Role.ROLE_USER);
            addUser("bruky", Role.ROLE_USER);
            addUser("saki", Role.ROLE_USER);
            addUser("salo", Role.ROLE_USER);
            addUser("sami", Role.ROLE_USER);
            addUser("sado", Role.ROLE_USER);
            addUser("silo", Role.ROLE_USER);
            addUser("soom", Role.ROLE_USER);
            addUser("soomo", Role.ROLE_USER);
            addUser("blomo", Role.ROLE_USER);
            addUser("klomo", Role.ROLE_USER);
            addUser("dlomo", Role.ROLE_USER);
            addUser("dlami", Role.ROLE_USER);
            addUser("dlemi", Role.ROLE_USER);
            addUser("drumi", Role.ROLE_USER);
            addUser("kuki2", Role.ROLE_USER);
            addUser("bruki12", Role.ROLE_USER);
            addUser("pepi3", Role.ROLE_USER);
            addUser("pepi33", Role.ROLE_USER);
            addUser("pepi333", Role.ROLE_USER);
            addUser("pepi3333", Role.ROLE_USER);
            addUser("pepi33333", Role.ROLE_USER);
            addUser("pepi4444", Role.ROLE_USER);
            addUser("pepi444", Role.ROLE_USER);
            addUser("pepi44", Role.ROLE_USER);
            addUser("pepi4", Role.ROLE_USER);
            addUser("pepi44444", Role.ROLE_USER);
            addUser("pepi444444", Role.ROLE_USER);
            addUser("pepi5", Role.ROLE_USER);
            addUser("pepi55", Role.ROLE_USER);
            addUser("pepi555", Role.ROLE_USER);
            addUser("pepi5555", Role.ROLE_USER);
            addUser("pepi55555", Role.ROLE_USER);
            addUser("pepi555555", Role.ROLE_USER);
            addUser("pepi6", Role.ROLE_USER);
            addUser("pepi66", Role.ROLE_USER);
            addUser("pepi666", Role.ROLE_USER);
            addUser("pepi6666", Role.ROLE_USER);
            addUser("pepi66666", Role.ROLE_USER);
            addUser("pepi666666", Role.ROLE_USER);
            addUser("pepi77", Role.ROLE_USER);
            addUser("pepi7", Role.ROLE_USER);
            addUser("pepi777", Role.ROLE_USER);
            addUser("pepi7777", Role.ROLE_USER);
            addUser("pepi77777", Role.ROLE_USER);
            addUser("pepi777777", Role.ROLE_USER);
            addUser("pepi8", Role.ROLE_USER);
            addUser("pepi88", Role.ROLE_USER);
            addUser("pepi888", Role.ROLE_USER);
            addUser("pepi8888", Role.ROLE_USER);
            addUser("pepi88888", Role.ROLE_USER);
            addUser("pepi888888", Role.ROLE_USER);
            addUser("solma", Role.ROLE_USER);
            addUser("solma1", Role.ROLE_USER);
            addUser("solma2", Role.ROLE_USER);
            addUser("solma3", Role.ROLE_USER);
            addUser("solma4", Role.ROLE_USER);
            addUser("solma5", Role.ROLE_USER);
            addUser("solma6", Role.ROLE_USER);
            addUser("solma7", Role.ROLE_USER);
            addUser("solma8", Role.ROLE_USER);
            addUser("solma9", Role.ROLE_USER);
            addUser("solma11", Role.ROLE_USER);
            addUser("solma12", Role.ROLE_USER);
            addUser("solma13", Role.ROLE_USER);
            addUser("solma14", Role.ROLE_USER);
            addUser("solma15", Role.ROLE_USER);
            addUser("solma16", Role.ROLE_USER);
            addUser("solma17", Role.ROLE_USER);
            addUser("solma18", Role.ROLE_USER);
            addUser("solma19", Role.ROLE_USER);
            addUser("solma10", Role.ROLE_USER);
        }
    }
}
