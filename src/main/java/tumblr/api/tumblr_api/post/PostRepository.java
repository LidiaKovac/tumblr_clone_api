package tumblr.api.tumblr_api.post;

import org.springframework.data.jpa.repository.JpaRepository;
import tumblr.api.tumblr_api.post.Post;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByMarkDownContentContainsIgnoreCase(String query);
}
