package tumblr.api.tumblr_api.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tumblr.api.tumblr_api.exceptions.BadRequestException;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;
import tumblr.api.tumblr_api.post.Post;
import tumblr.api.tumblr_api.post.PostRepository;
import tumblr.api.tumblr_api.services.IService;

import java.util.List;
import java.util.UUID;

@Service
public class CommentsService implements IService<Comment, NewCommentDTO, NewCommentDTO> {
    @Autowired CommentsRepo repo;

    @Override
    public Comment save(NewCommentDTO obj) throws BadRequestException {
        return this.repo.save(
                new Comment(obj.content(), obj.user(), obj.post())
        );
    }

    @Override
    public Comment findById(UUID id) throws ElementNotFoundException {
        return null;
    }

    @Override
    public List<Comment> find() {
        return null;
    }

    @Override
    public Comment findByIdAndUpdate(UUID id, NewCommentDTO obj) throws Exception {
        return null;
    }

    @Override
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException {    }
}
