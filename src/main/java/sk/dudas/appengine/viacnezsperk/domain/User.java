package sk.dudas.appengine.viacnezsperk.domain;

import com.google.appengine.api.datastore.Text;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 15.10.2012
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */
@Entity(name = "user")
public class User extends BaseEntity implements UserDetails {

    private String username;
    private String password;

    private String firstname;
    private String lastname;
    private Integer age;
    private Integer bornYear;
    private String residence;
    private String socialInfo;
    private Date created;
    private String createdBy;
    private Date modified;
    private String modifiedBy;
    private String mainURL;
    private Text content;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Role.class)
    private Collection<Role> roles;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(String rolename) {
        if (roles == null || roles.size() == 0) {
            return false;
        } else {
            for (Role role : roles) {
                if (role.getName().equals(rolename)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAdmin() {
        return this.hasRole(Role.ROLE_ADMIN);
    }

    public boolean isUser() {
        return this.hasRole(Role.ROLE_USER);
    }

    public boolean isValidMainURL() {
        return mainURL != null && !mainURL.isEmpty();
    }

    public boolean isValidFirstname() {
        return firstname != null && !firstname.isEmpty();
    }

    public boolean isValidBornYear() {
        return bornYear != null;
    }

    public boolean isValidResidence() {
        return residence != null && !residence.isEmpty();
    }

    public boolean isValidSocialInfo() {
        return socialInfo != null && !socialInfo.isEmpty();
    }

    public String getFullname() {
        String firstname = getFirstname() != null ? getFirstname() : Constants.EMPTY;
        String lastname = getLastname() != null ? getLastname() : Constants.EMPTY;
        return firstname + " " + lastname;
    }

    public String getAllNames() {
        String username = getUsername() != null ? getUsername() : Constants.EMPTY;
        return username + " " + getFullname();
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getMainURL() {
        return mainURL;
    }

    public void setMainURL(String mainURL) {
        this.mainURL = mainURL;
    }

    public Text getContent() {
        return content;
    }

    public void setContent(Text content) {
        this.content = content;
    }

    public Integer getBornYear() {
        return bornYear;
    }

    public void setBornYear(Integer bornYear) {
        this.bornYear = bornYear;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getSocialInfo() {
        return socialInfo;
    }

    public void setSocialInfo(String socialInfo) {
        this.socialInfo = socialInfo;
    }
}
