package sk.dudas.appengine.viacnezsperk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.service.UserManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 15.10.2012
 * Time: 22:19
 * To change this template use File | Settings | File Templates.
 */
//@Controller("userController")
//public class UserController {
//
//    private static final Logger logger = Logger.getLogger(UserController.class.getName());
//
//    @Autowired
//    private UserManager userManager;
//
//    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
//    public String load() {
//        logger.log(Level.INFO, "Adding user.");
//
//        User entity = new User();
//        entity.setUsername("Alfonz");
//        entity.setPassword(new Md5PasswordEncoder().encodePassword("alfonz", null));
//        userManager.persist(entity);
//
//        return "home";
//    }
//}
