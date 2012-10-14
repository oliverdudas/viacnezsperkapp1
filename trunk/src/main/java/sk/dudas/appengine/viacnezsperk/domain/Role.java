package sk.dudas.appengine.viacnezsperk.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 15.10.2012
 * Time: 18:08
 * To change this template use File | Settings | File Templates.
 */
@Entity(name="role")
public class Role extends BaseEntity implements GrantedAuthority {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    private String name;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public String getAuthority() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
