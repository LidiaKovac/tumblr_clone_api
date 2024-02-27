package tumblr.api.tumblr_api.images;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tumblr.api.tumblr_api.post.Post;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Image {
    @Id
    @GeneratedValue
    UUID id;

    String url;

    @ManyToOne(optional = true)
    Post post;

    public Image(String url, Post post) {
        this.url = url;
        this.post = post;
    }
    public Image(String url) {
        this.url = url;
    }
}
