package tumblr.api.tumblr_api.likes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import tumblr.api.tumblr_api.entities.Note;
import tumblr.api.tumblr_api.entities.NoteType;
import tumblr.api.tumblr_api.post.Post;
import tumblr.api.tumblr_api.user.User;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "likes")
public class Like extends Note {
    @Id
    @GeneratedValue
    UUID id;

    @CreationTimestamp
    Date createdAt;

    @ManyToOne
    User user;

    @ManyToOne
    @JsonIgnore
    private Post post;




    @Transient
    NoteType type = NoteType.LIKE;
    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
