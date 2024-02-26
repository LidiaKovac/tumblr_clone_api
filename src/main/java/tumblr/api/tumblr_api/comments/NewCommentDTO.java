package tumblr.api.tumblr_api.comments;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import tumblr.api.tumblr_api.post.Post;
import tumblr.api.tumblr_api.user.User;

public record NewCommentDTO(
        @NotEmpty(message = "Post must have at least 1 character.")
        @Size(max = 100000, message = "Your post exceeds the 100k character limit")
        String content,
        User user,
        Post post
) {
}
