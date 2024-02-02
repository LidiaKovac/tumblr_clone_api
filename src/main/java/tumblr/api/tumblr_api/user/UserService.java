package tumblr.api.tumblr_api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tumblr.api.tumblr_api.services.IService;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IService<User, UserDTO> {

    @Autowired
    UserRepository repo;

    @Override
    public User save(UserDTO obj) {
        if(obj.avatar() != null) return this.repo.save(new User(obj.email(), obj.name(), obj.blogTitle(), obj.password(), obj.avatar()));
        else return this.repo.save(new User(obj.email(), obj.name(), obj.blogTitle(), obj.password()));
    }

    @Override
    public User findById(UUID id) throws ElementNotFoundException {
        return this.repo.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
    }

    @Override
    public List<User> find() {
        return this.repo.findAll();
    }

    public List<User> findByEmail(String email) {
        return this.repo.findOneByEmail(email);
    }
    @Override
    public User findByIdAndUpdate(UUID id, UserDTO obj) throws Exception {
        User found = this.repo.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
        found.setBlogTitle(obj.blogTitle());
        found.setName(obj.name());
        this.repo.save(found);
        return found;
    }

    @Override
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException {
        User found = this.repo.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
        repo.delete(found);
    }
}
