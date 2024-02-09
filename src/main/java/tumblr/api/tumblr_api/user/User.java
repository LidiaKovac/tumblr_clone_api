package tumblr.api.tumblr_api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tumblr.api.tumblr_api.entities.IEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends IEntity  {
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

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name="users_follower", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name="follower_id"))
    private List<User> followers;
    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name="users_following", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name="following_id"))
    private List<User> following;

    public User(String email, String name, String blogTitle, String password, String avatar) {
        this.email = email;
        this.name = name;
        this.blogTitle = blogTitle;
        this.password = password;
        this.avatar = avatar;
    }

    public User(String email, String name, String blogTitle, String password) {
        this.email = email;
        this.name = name;
        this.blogTitle = blogTitle;
        this.password = password;
        this.avatar = "http://placehold.it/300x300";
    }


}
