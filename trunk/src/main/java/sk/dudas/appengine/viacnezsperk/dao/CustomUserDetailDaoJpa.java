package sk.dudas.appengine.viacnezsperk.dao;

import com.google.appengine.api.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import sk.dudas.appengine.viacnezsperk.domain.Role;
import sk.dudas.appengine.viacnezsperk.domain.User;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: oli
 * Date: 21.4.2011
 * Time: 0:21
 * To change this template use File | Settings | File Templates.
 */

@Repository("customUserDetailDao")
public class CustomUserDetailDaoJpa extends BaseDaoJpa<Key, User> implements UserDetailDao {

    public CustomUserDetailDaoJpa() {
        super(User.class);
    }

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }

    public UserDetails loadUserByUsername(final String username) {
        List<User> list = getJpaTemplate().execute(new JpaCallback<List<User>>() {

            public List<User> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createQuery("SELECT h FROM " + User.class.getName() + " h WHERE h.username=:username");
                query.setParameter("username", username);
                List<User> resultList = (List<User>) query.getResultList();
                resultList.size();
                return resultList;
            }
        });
        int size = list.size();
        if (size == 1) {
            return list.get(0);
        } else if (size > 1) {
            throw new DataRetrievalFailureException("Retrieved more then one entity. Expected count: " + 1);
        } else {
            return null;
        }
    }

    public Role loadRoleByRolename(final String rolename) {
        List<Role> list = getJpaTemplate().execute(new JpaCallback<List<Role>>() {

            public List<Role> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createQuery("SELECT h FROM " + Role.class.getName() + " h WHERE h.name=:rolename");
                query.setParameter("rolename", rolename);
                List<Role> resultList = (List<Role>) query.getResultList();
                resultList.size();
                return resultList;
            }
        });
        int size = list.size();
        if (size == 1) {
            return list.get(0);
        } else if (size > 1) {
            throw new DataRetrievalFailureException("Retrieved more then one entity. Expected count: " + 1);
        } else {
            return null;
        }
    }
}
