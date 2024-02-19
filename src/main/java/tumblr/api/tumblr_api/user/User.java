package tumblr.api.tumblr_api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tumblr.api.tumblr_api.entities.IEntity;
import tumblr.api.tumblr_api.entities.Roles;

import java.util.*;

@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends IEntity implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    @Column(unique = true, name = "email")
    private String email;
    private String blogTitle;
    @JsonIgnore
    private String password;
    private String avatar;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name="users_follower", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name="follower_id"))
    private List<User> followers;
    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name="users_following", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name="following_id"))
    private List<User> following;

//    @JsonIgnore
//    @Transient
//    boolean enabled;
//    @JsonIgnore
//    @Transient
//    SimpleGrantedAuthority authorities;
//    @JsonIgnore
//    @Transient
//    String username;
//    @JsonIgnore
//    @Transient
//    boolean accountNonLocked;
//    @JsonIgnore
//    @Transient
//    boolean accountNonExpired;
//    @JsonIgnore
//    @Transient
//    boolean credentialsNonExpired;

    public User(String email, String name, String blogTitle, String password, String avatar) {
        this.email = email;
        this.name = name;
        this.blogTitle = blogTitle;
        this.password = password;
        this.avatar = avatar;
        this.role = Roles.BASIC;
    }

    public User(String email, String name, String blogTitle, String password) {
        this.email = email;
        this.name = name;
        this.blogTitle = blogTitle;
        this.password = password;
        this.avatar = "http://placehold.it/300x300";
        this.role = Roles.BASIC;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", blogTitle='" + blogTitle + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role=" + role +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }
}
