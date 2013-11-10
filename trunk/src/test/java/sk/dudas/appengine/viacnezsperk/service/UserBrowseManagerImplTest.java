package sk.dudas.appengine.viacnezsperk.service;

import com.google.appengine.api.datastore.Key;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sk.dudas.appengine.viacnezsperk.AbstractSecurityTest;
import sk.dudas.appengine.viacnezsperk.domain.GalleryItem;
import sk.dudas.appengine.viacnezsperk.domain.Role;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.domain.UserBrowse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 19.10.2012
 * Time: 19:25
 * To change this template use File | Settings | File Templates.
 */
public class UserBrowseManagerImplTest extends AbstractSecurityTest {

    @Autowired
    @Qualifier(value = "userBrowseManager")
    protected UserBrowseManager userBrowseManager;

    @Test
    public void testGetBrowseUsers() throws Exception {
        List<UserBrowse> all = userBrowseManager.findAll();
        assertNotNull(all);
    }
}
