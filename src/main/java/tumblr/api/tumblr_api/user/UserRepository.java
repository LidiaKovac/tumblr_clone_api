package tumblr.api.tumblr_api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import tumblr.api.tumblr_api.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
   Optional<User> findOneByEmail(String email);
}
