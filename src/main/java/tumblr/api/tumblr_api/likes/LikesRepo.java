package tumblr.api.tumblr_api.likes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikesRepo extends JpaRepository<Like, UUID> {}
