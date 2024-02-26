package tumblr.api.tumblr_api.comments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentsRepo extends JpaRepository<Comment, UUID> {
}
