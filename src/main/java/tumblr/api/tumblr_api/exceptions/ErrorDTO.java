package tumblr.api.tumblr_api.exceptions;

import java.time.LocalDateTime;

public record ErrorDTO(
        String message,
        LocalDateTime thrownAt
) {
}
