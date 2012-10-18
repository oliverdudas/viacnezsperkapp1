package sk.dudas.appengine.viacnezsperk.controller.bind;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import java.beans.PropertyEditorSupport;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 19.10.2012
 * Time: 22:45
 * To change this template use File | Settings | File Templates.
 */
public class CustomUserPasswordBinder extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        return "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        PasswordEncoder encoder = new Md5PasswordEncoder();
        String hashedPass = encoder.encodePassword(text, null);
        setValue(hashedPass);
    }
}
