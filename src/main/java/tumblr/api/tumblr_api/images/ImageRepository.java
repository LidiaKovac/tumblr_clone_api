package tumblr.api.tumblr_api.images;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    public List<Image> getAllByPostId(UUID postId);
}
