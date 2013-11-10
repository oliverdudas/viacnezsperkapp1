package sk.dudas.appengine.viacnezsperk.service;

import com.google.appengine.api.datastore.Key;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import sk.dudas.appengine.viacnezsperk.AbstractSecurityTest;
import sk.dudas.appengine.viacnezsperk.domain.GalleryItem;
import sk.dudas.appengine.viacnezsperk.domain.Role;
import sk.dudas.appengine.viacnezsperk.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 19.10.2012
 * Time: 19:25
 * To change this template use File | Settings | File Templates.
 */
public class UserManagerImplTest extends AbstractSecurityTest {

    @Autowired
    private PicasaManager picasaManager;

    @Test
    public void testGetUsers() throws Exception {
        List<User> all = userManager.findAll();
        System.out.println(all.size());
    }

//    @Test
//    public void testUpload() throws Exception {
//        picasaManager.getAlbumIdWithFreeSpaceLeft(null);
//    }


    @Test
    public void testFindAllUsers() throws Exception {
        List<User> all = userManager.findAll();
        for (User user : all) {
            long id = user.getKey().getId();
            User byId = userManager.findById(id);
            System.out.println(byId);
        }

        System.out.println(all.size());
    }

    @Test
    public void testFindUsers() throws Exception {
        int count = 5;
        List<User> users = userManager.find(count);
        assertEquals(count, users.size());
    }

    @Test
    public void testGalleryItems() throws Exception {
        User galleryUser = new User();
        galleryUser.setPassword("1111");
        galleryUser.setUsername("GalleryUser");
        galleryUser.setFirstname("GalleryUser");
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(new Role(Role.ROLE_USER));
        galleryUser.setRoles(roles);
        galleryUser.addGalleryItem(new GalleryItem("1", "http://www.asdfasdf.sk", "http://www.asdfasdf.sk"));
        galleryUser.addGalleryItem(new GalleryItem("2", "http://www.ggggg.sk", "http://www.asdfasdf.sk"));
        galleryUser.addGalleryItem(new GalleryItem("2", "http://www.bbbbb.sk", "http://www.asdfasdf.sk"));
        userManager.persistEntity(galleryUser);

        Key key = galleryUser.getKey();
        User user = userManager.findById(key);

        Collection<GalleryItem> galleryItems = user.getGalleryItems();
        System.out.println("User: " + user.getFullname() + " has " + galleryItems.size() + " gallery items.");
        for (GalleryItem galleryItem : galleryItems) {
            System.out.println("Item " + galleryItem.getIndex() + " with imageUrl: " + galleryItem.getImageUrl());
        }
    }
}
