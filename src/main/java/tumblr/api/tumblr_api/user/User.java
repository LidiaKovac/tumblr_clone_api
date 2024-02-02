package tumblr.api.tumblr_api.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tumblr.api.tumblr_api.entities.IEntity;

import java.util.UUID;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends IEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String email;
    private String blogTitle;
    private String password;
    private String avatar;

    @OneToMany
    private User[] followers;
    @OneToMany
    private User[] following;

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
