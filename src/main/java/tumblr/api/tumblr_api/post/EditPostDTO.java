package tumblr.api.tumblr_api.post;

import jakarta.validation.constraints.Size;

import java.util.List;

public record EditPostDTO(
        @Size(max = 100000, message = "Your post exceeds the 100k character limit")
        String markDownContent,
        List<String> images,
        List<String> tags
) {
}
