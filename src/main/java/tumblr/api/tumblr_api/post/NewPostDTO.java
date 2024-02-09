package tumblr.api.tumblr_api.post;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record NewPostDTO(
        @NotEmpty(message = "Post must have at least 1 character.")
        @Size(max = 100000, message = "Your post exceeds the 100k character limit")
        String markDownContent,
        List<MultipartFile> imageFiles,
        List<String> images,
        List<String> tags,

        @NotEmpty(message = "Who does this post belong to?")
        @Email(message = "This is not a valid email")
        String userEmail
) {
}
