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
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dao.loadUserByUsername(username);
    }
}
