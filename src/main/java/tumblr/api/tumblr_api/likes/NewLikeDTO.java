package tumblr.api.tumblr_api.likes;

import tumblr.api.tumblr_api.post.Post;
import tumblr.api.tumblr_api.user.User;

import java.util.Date;

public record NewLikeDTO(
        User user,
        Post post
) {
}
