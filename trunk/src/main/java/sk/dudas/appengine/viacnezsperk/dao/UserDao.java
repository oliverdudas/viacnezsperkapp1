package sk.dudas.appengine.viacnezsperk.dao;

import com.google.appengine.api.datastore.Key;
import org.springframework.security.core.userdetails.UserDetails;
import sk.dudas.appengine.viacnezsperk.domain.Role;
import sk.dudas.appengine.viacnezsperk.domain.User;

/**
 * Created by IntelliJ IDEA.
 * User: oli
 * Date: 21.4.2011
 * Time: 0:20
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao extends BaseDao<Key, User> {

}
