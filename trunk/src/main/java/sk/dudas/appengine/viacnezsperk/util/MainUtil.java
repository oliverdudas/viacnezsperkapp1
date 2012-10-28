package sk.dudas.appengine.viacnezsperk.util;

import org.springframework.security.core.context.SecurityContextHolder;
import sk.dudas.appengine.viacnezsperk.domain.User;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 22.10.2012
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */
public class MainUtil {

    public static User getLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
