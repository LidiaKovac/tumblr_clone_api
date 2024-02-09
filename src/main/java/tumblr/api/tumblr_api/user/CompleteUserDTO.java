package tumblr.api.tumblr_api.user;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

public record CompleteUserDTO(
        UUID id,
        String name,
        String email,
        String blogTitle,
        String avatar,
        int followers,
        int following

) {


}
