package tumblr.api.tumblr_api.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tumblr.api.tumblr_api.entities.IEntity;
import tumblr.api.tumblr_api.user.User;

import java.util.UUID;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Setter
@Getter
public class Post extends IEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String markDownContent;
    private String[] images;
    private String[] tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String content, String[] images, String[] tags, User user) {
        this.markDownContent = content;
        this.images = images;
        this.tags = tags;
        this.setUser(user);
    }
}
