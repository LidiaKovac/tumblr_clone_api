package tumblr.api.tumblr_api.post;

import jakarta.validation.constraints.Size;

public record EditPostDTO(
        @Size(max = 100000, message = "Your post exceeds the 100k character limit")
        String markDownContent,
        String[] images,
        String[] tags
) {
}
