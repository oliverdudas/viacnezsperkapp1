package sk.dudas.appengine.viacnezsperk.controller.cron;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: oli
 * Date: 3.4.2011
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */

@Controller("loadController")
public class LoadController {

    private static final Logger logger = Logger.getLogger(LoadController.class.getName());

    @RequestMapping(value = "/cron/load", method = RequestMethod.GET)
    public String load() {
        logger.log(Level.INFO, "Cron load request.");
        return "templates/empty";
    }
}
