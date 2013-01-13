package sk.dudas.appengine.viacnezsperk.controller.validator;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.util.common.base.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.service.UserManager;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 30.10.2012
 * Time: 20:40
 * To change this template use File | Settings | File Templates.
 */
@Component(value = "userValidator")
public class UserValidator implements Validator {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserManager userManager;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required", "required");

        Key key = user.getKey();
        if (key == null) {
            if (StringUtil.isEmptyOrWhitespace(user.getPassword())) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required", "required");
            }

            String username = user.getUsername();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                errors.rejectValue("username", "error.username.already.exists", "error.username.already.exists");
            }

        } else {
            User userByKey = userManager.findById(key);
            String userByKeyUsername = userByKey.getUsername();

            if (!StringUtil.isEmptyOrWhitespace(user.getUsername())) {
                if (!user.getUsername().equals(userByKeyUsername)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
                    if (userDetails != null) {
                        errors.rejectValue("username", "error.username.already.exists", "error.username.already.exists");
                    }
                }
            }

        }

    }
}
