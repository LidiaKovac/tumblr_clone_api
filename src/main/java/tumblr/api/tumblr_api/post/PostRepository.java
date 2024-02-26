package tumblr.api.tumblr_api.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tumblr.api.tumblr_api.post.Post;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByMarkDownContentContainsIgnoreCase(String query);
    // HQL
    @Query("select p from Post p where array_contains(p.tags, :tag)")
    List<Post> findAllByTagsContaining(@Param("tag") String tag);
    List<Post> findByUserId(UUID id);

//    @Query("select p from Post p where p.isReblog=true and p.originalPostId=:id")
    List<Post> findAllByOriginalPostId(UUID id);
}
