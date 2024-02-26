package tumblr.api.tumblr_api.comments;

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
@Table(name= "comments")
@NoArgsConstructor
@Setter
@Getter
public class Comment extends Note {
    @GeneratedValue
    @Id
    UUID id;

    String content;

    @ManyToOne()
    private User user;
    @ManyToOne()
    @JsonIgnore
    private Post post;



    @CreationTimestamp
    Date createdAt;

    @Transient
    NoteType type = NoteType.COMMENT;

    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
//        this.post_id = post.getId();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", type=" + type +
                '}';
    }
}
