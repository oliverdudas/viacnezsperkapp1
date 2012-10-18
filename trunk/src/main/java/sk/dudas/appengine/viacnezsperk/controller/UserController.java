package sk.dudas.appengine.viacnezsperk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.service.UserManager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 15.10.2012
 * Time: 22:19
 * To change this template use File | Settings | File Templates.
 */
@Controller("userController")
@SessionAttributes(value = "child")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/admin/children", method = RequestMethod.GET)
    public void list(ModelMap modelMap) {
        logger.log(Level.INFO, "Zoznam deti");

        List<User> all = userManager.findAll();
        modelMap.addAttribute("all", all);
    }

    @RequestMapping(value = "/admin/childForm", method = RequestMethod.GET)
    public void form(ModelMap modelMap, @RequestParam(required = false) Long id) {
        if (id == null) {
            logger.log(Level.INFO, "Creating child.");
            modelMap.addAttribute("child", new User());
        } else {
        }


        List<User> all = userManager.findAll();
        modelMap.addAttribute("all", all);
    }
}
