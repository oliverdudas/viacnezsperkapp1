package sk.dudas.appengine.viacnezsperk.dao;

import com.google.appengine.api.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import sk.dudas.appengine.viacnezsperk.domain.User;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: oli
 * Date: 21.4.2011
 * Time: 0:21
 * To change this template use File | Settings | File Templates.
 */

@Repository("userDao")
public class UserDaoJpa extends BaseDaoJpa<Key, User> implements UserDao {

    public UserDaoJpa() {
        super(User.class);
    }

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }

}
