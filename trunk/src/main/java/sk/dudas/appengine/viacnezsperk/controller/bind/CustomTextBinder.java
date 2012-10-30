package sk.dudas.appengine.viacnezsperk.controller.bind;

import com.google.appengine.api.datastore.Text;
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
public class CustomTextBinder extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        Text value = (Text) getValue();
        if (value != null) {
            return value.getValue();
        }
        return "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(new Text(text));
    }
}
