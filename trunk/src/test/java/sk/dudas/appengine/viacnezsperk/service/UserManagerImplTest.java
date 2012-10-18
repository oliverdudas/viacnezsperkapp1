package sk.dudas.appengine.viacnezsperk.service;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import sk.dudas.appengine.viacnezsperk.AbstractSecurityTest;
import sk.dudas.appengine.viacnezsperk.domain.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 19.10.2012
 * Time: 19:25
 * To change this template use File | Settings | File Templates.
 */
public class UserManagerImplTest extends AbstractSecurityTest {

    @Test
    @Rollback(value = true)
    public void testGetUsers() throws Exception {
        List<User> all = userManager.findAll();
        System.out.println(all.size());
    }
}
