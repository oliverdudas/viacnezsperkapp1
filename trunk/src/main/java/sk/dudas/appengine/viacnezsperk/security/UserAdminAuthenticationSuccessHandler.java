package sk.dudas.appengine.viacnezsperk.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sk.dudas.appengine.viacnezsperk.domain.Role;
import sk.dudas.appengine.viacnezsperk.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 28.10.2012
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public class UserAdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Map<String, String> roleUrlMap;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) authentication.getPrincipal();
            if (user.hasAdminRole()) {
                response.sendRedirect(request.getContextPath() + roleUrlMap.get(Role.ROLE_ADMIN));
            } else if (user.hasUserRole()) {
                response.sendRedirect(request.getContextPath() + roleUrlMap.get(Role.ROLE_USER));
            }
        }
    }

    public void setRoleUrlMap(Map<String, String> roleUrlMap) {
        this.roleUrlMap = roleUrlMap;
    }
}
