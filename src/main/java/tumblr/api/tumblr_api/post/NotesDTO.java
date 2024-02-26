package tumblr.api.tumblr_api.post;

import tumblr.api.tumblr_api.comments.Comment;
import tumblr.api.tumblr_api.likes.Like;

import java.util.List;

public record NotesDTO(
        List<Comment> comments,
        List<Like> likes,
        List<Post> reblogs,
        int total
) {
}
