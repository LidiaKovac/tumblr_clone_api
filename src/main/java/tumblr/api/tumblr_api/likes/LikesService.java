package tumblr.api.tumblr_api.likes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tumblr.api.tumblr_api.exceptions.BadRequestException;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;
import tumblr.api.tumblr_api.post.Post;
import tumblr.api.tumblr_api.services.IService;
import tumblr.api.tumblr_api.user.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class LikesService implements IService<Like, NewLikeDTO, NewLikeDTO> {

    @Autowired
    LikesRepo repo;

    @Override
    public Like save(NewLikeDTO obj) throws BadRequestException {
        return this.repo.save(new Like(obj.user(), obj.post()));
    }

    @Override
    public Like findById(UUID id) throws ElementNotFoundException {
        return null;
    }

    @Override
    public List<Like> find() {
        return null;
    }

    @Override
    public Like findByIdAndUpdate(UUID id, NewLikeDTO obj) throws Exception {
        return null;
    }

    @Override
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException {

    }
}
