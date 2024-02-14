package tumblr.api.tumblr_api.user;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tumblr.api.tumblr_api.config.CloudinaryConfig;
import tumblr.api.tumblr_api.exceptions.BadRequestException;
import tumblr.api.tumblr_api.services.IService;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IService<User, NewUserDTO, EditUserDTO> {

    @Autowired
    UserRepository repo;

    @Autowired
    Cloudinary cloudinary;

    @Override
    public User save(NewUserDTO obj) {
        return this.repo.save(new User(obj.email(), obj.name(), obj.blogTitle(), obj.password(), obj.avatar()));
    }

    @Override
    public User findById(UUID id) throws ElementNotFoundException {
        return this.repo.findById(id).orElseThrow(() -> new ElementNotFoundException(id.toString()));
    }

    @Override
    public List<User> find() {
        return this.repo.findAll();
    }

    public User findByEmail(String email) throws ElementNotFoundException {
        User found = this.repo.findOneByEmail(email);
        if(found != null) {
            return found;
        } else throw new ElementNotFoundException(email);
    }

    @Override
    public User findByIdAndUpdate(UUID id, EditUserDTO obj) throws Exception {
        User found = this.repo.findById(id).orElseThrow(() -> new ElementNotFoundException(id.toString()));
        found.setBlogTitle(obj.blogTitle());
        found.setName(obj.name());
        this.repo.save(found);
        return found;
    }

    @Override
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException {
        User found = this.repo.findById(id).orElseThrow(() -> new ElementNotFoundException(id.toString()));
        repo.delete(found);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        return (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }

    public User addFollower(UUID id, UUID followId) throws ElementNotFoundException {
        User found = this.findById(id);
        User toFollow = this.findById(followId);
//        Set person A following person B
        List<User> foll = found.getFollowing();
        if(foll.stream().map((user) -> user.getId()).toList().contains(followId)) {
            throw new BadRequestException("You are already following this person.");
        }
        foll.add(toFollow);
        found.setFollowing(foll);
//        Set person B being followed by Person A
        List<User> followers = toFollow.getFollowers();
        followers.add(found);
        toFollow.setFollowers(followers);
        return this.repo.save(found);
    }
}
