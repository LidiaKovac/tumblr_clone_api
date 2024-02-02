package tumblr.api.tumblr_api.post;

import tumblr.api.tumblr_api.exceptions.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;
import tumblr.api.tumblr_api.services.IService;
import tumblr.api.tumblr_api.user.User;
import tumblr.api.tumblr_api.user.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PostService implements IService<Post, PostDTO> {

    @Autowired
    PostRepository repo;
    @Autowired
    UserRepository userRepo;

    @Override
    public Post save(PostDTO obj) throws BadRequestException {
        List<User> owner = this.userRepo.findOneByEmail(obj.userEmail());
        if(!owner.isEmpty()) {
            return this.repo.save(new Post(obj.markDownContent(), obj.images(), obj.tags(), owner.get(0)));
        } else throw new BadRequestException("This post doesn't seem to belong to anybody.");
    }

    @Override
    public Post findById(UUID id) throws ElementNotFoundException {
        return this.repo.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
    }

    @Override
    public List<Post> find() {
        return this.repo.findAll();
    }

    public List<Post> findByContent(String query) {
        return this.repo.findByMarkDownContentContainsIgnoreCase(query);
    }
    @Override
    public Post findByIdAndUpdate(UUID id, PostDTO obj) throws Exception {
        Post found = this.findById(id);
        found.setImages(obj.images());
        found.setMarkDownContent(obj.markDownContent());
        this.repo.save(found);
        return found;
    }

    @Override
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException {
        Post found = this.findById(id);
        this.repo.delete(found);
    }
}
