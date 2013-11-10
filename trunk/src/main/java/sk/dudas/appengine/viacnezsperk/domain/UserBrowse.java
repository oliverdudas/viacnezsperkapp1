package sk.dudas.appengine.viacnezsperk.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 10.11.2013
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
@Entity(name = "userBrowse")
@Table(name = "User")
public class UserBrowse extends BaseEntity {

    private String username;
    private String firstname;
    private String lastname;
    private Date modified;
    private Date created;

    public String getAllNames() {
        String username = getUsername() != null ? getUsername() : Constants.EMPTY;
        return username + " " + getFullname();
    }

    public String getFullname() {
        String firstname = getFirstname() != null ? getFirstname() : Constants.EMPTY;
        String lastname = getLastname() != null ? getLastname() : Constants.EMPTY;
        return firstname + " " + lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
