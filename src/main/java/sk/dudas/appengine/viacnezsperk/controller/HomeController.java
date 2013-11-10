package sk.dudas.appengine.viacnezsperk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.service.UserManager;
import sk.dudas.appengine.viacnezsperk.util.MainUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 15.10.2012
 * Time: 22:19
 * To change this template use File | Settings | File Templates.
 */
@Controller("homeController")
public class HomeController {

    private static final Logger logger = Logger.getLogger(HomeController.class.getName());

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome() {
        return "redirect:/home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String list(ModelMap modelMap, @RequestParam(defaultValue = "0", required = false) long id) {
        logger.log(Level.INFO, "Home");

        boolean showCloseHeaderType = false;
        User loggedUser = MainUtil.getLoggedUser();
        User user;
        if (loggedUser.isAdmin()) {
            if (id != 0) {
                user = userManager.findUserById(id);
                showCloseHeaderType = true;
            } else {
                return "redirect:" + UserController.ADMIN_CHILDREN_VIEW;
            }
        } else {
            user = loggedUser;
        }

        modelMap.addAttribute("user", user);
        modelMap.addAttribute("showCloseHeaderType", showCloseHeaderType);

        return "home";
    }

}
