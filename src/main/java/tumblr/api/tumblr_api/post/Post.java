package tumblr.api.tumblr_api.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import tumblr.api.tumblr_api.comments.Comment;
import tumblr.api.tumblr_api.entities.IEntity;
import tumblr.api.tumblr_api.likes.Like;
import tumblr.api.tumblr_api.user.User;

import java.util.ArrayList;
import java.util.Date;
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

    @Column
    private int notes = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Like> likes;

    @Transient
    private boolean isLikedByUser;

    @Column(name = "isReblog")
    private boolean isReblog = false;


    @OneToMany(mappedBy = "originalPost")
    @JsonIgnore
    private List<Post> reblogs;

    @ManyToOne
    private Post originalPost;



    @CreationTimestamp
    Date createdAt;

    public Post(String content, List<String> images, List<String> tags, User user) {
        this.markDownContent = content;
        this.images = images;
        this.tags = tags;
        this.setUser(user);
        this.isReblog = false;
        this.notes = 0;

    }

    public Post(String content, List<String> tags, User user, Post originalPost) {
        this.markDownContent = content;
        this.tags = tags;
        this.isReblog = true;
        this.originalPost = originalPost;
        this.user = user;
        this.notes = 0;
    }

//    public int getNotes() {
//        return this.likes.size() + this.reblogs.size() + this.comments.size();
//    }

    public void increaseNotes() {
        this.notes++;
    }

    public void decreaseNote() {
        this.notes--;
    }

    public Post addLike(Like like) {
        this.likes.add(like);
        return this;
    }

    public Post removeLike(Like like) {
                List<Like> newLikes = new ArrayList<Like>();
                this.likes.forEach(
                        l -> {
                            if (!l.getId().equals(like.getId())) {
                                newLikes.add(l);
                            }
                        }
                );
                this.setLikes(newLikes);
            return this;
    }
    public Post addComment(Comment comment) {
        this.comments.add(comment);
        return this;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", markDownContent='" + markDownContent + '\'' +
                ", images=" + images +
                ", tags=" + tags +
                ", notes=" + notes +
                ", createdAt=" + createdAt +
                '}';
    }
}
