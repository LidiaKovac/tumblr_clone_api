package tumblr.api.tumblr_api.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tumblr.api.tumblr_api.controllers.IController;
import tumblr.api.tumblr_api.exceptions.BadRequestException;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;
import tumblr.api.tumblr_api.post.NewPostDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:5173/"})

public class UserController implements IController<User, NewUserDTO, EditUserDTO> {

    @Autowired
    UserService userSrv;

    @Override
    public User findById(UUID id) throws ElementNotFoundException {
        return null;
    }

    //    @Override
    @GetMapping("/{id}")
    public CompleteUserDTO findByIdComplete(@PathVariable UUID id) throws ElementNotFoundException {
        User found = this.userSrv.findById(id);
//        System.out.println();
        return new CompleteUserDTO(
                found.getId(),
                found.getName(),
                found.getEmail(),
                found.getBlogTitle(),
                found.getAvatar(),
                found.getFollowers().size(),
                found.getFollowing().size()
        );
    }

    @GetMapping("/{id}/followers")
    public List<User> getFollowersById(@PathVariable UUID id) throws ElementNotFoundException {
        User found = this.userSrv.findById(id);
        return found.getFollowers();
    }

    @GetMapping("/{id}/following")
    public List<User> getFollowingById(@PathVariable UUID id) throws ElementNotFoundException {
        User found = this.userSrv.findById(id);
        return found.getFollowing();
    }

    @Override
    @GetMapping("")
    public List<User> find(String email) throws Exception {
        if (email != null) {
            return this.userSrv.findByEmail(email);
        } else return this.userSrv.find();
    }

    @Override
    @DeleteMapping("/{id}")
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException {

    }

    @Override
    @PutMapping("/{id}")
    public User findByIdAndUpdate(UUID id, EditUserDTO body, BindingResult validation) throws Exception {
        return null;
    }

    @PutMapping("/{id}/follow/{followId}")
    public User follow(@PathVariable UUID id, @PathVariable UUID followId) throws ElementNotFoundException {
        return this.userSrv.addFollower(id, followId);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(
            @ModelAttribute NewUserDTO body,
            BindingResult validation
    ) throws IOException {
        if (validation.hasErrors()) throw new BadRequestException(validation.toString());
//        NewPostDTO created = new NewPostDTO(markDownContent, images, tags, userEmail);
//        if (bname == null || email == null || blogTitle == null || password == null)
//            throw new BadRequestException("name, email, blogTitle and password are required");
        if (body.avatarFile() == null) {
            NewUserDTO created = new NewUserDTO(
                    body.name(),
                    body.email(),
                    body.blogTitle(),
                    body.password(),
                    null,
                    "http://placehold.it/300x300");
            return this.userSrv.save(created);

        } else {
            System.out.println(body.avatar());
            String url = this.userSrv.uploadImage(body.avatarFile());
            NewUserDTO created = new NewUserDTO(
                    body.name(),
                    body.email(),
                    body.blogTitle(),
                    body.password(),
                    null,
                    url);
            return this.userSrv.save(created);
        }

    }
}
