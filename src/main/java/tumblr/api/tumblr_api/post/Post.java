package tumblr.api.tumblr_api.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;
import tumblr.api.tumblr_api.entities.IEntity;
import tumblr.api.tumblr_api.user.User;

import java.util.List;
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
    private List<String> images;
    private List<String> tags;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String content, List<String> images, List<String> tags, User user) {
        this.markDownContent = content;
        this.images = images;
        this.tags = tags;
        this.setUser(user);
    }
}
