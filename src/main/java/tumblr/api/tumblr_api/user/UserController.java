package tumblr.api.tumblr_api.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tumblr.api.tumblr_api.controllers.IController;
import tumblr.api.tumblr_api.entities.Roles;
import tumblr.api.tumblr_api.exceptions.BadRequestException;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;
import tumblr.api.tumblr_api.post.NewPostDTO;

import javax.management.relation.Role;
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
                found.getFollowing().size(),
                found.getRole().name()
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
    @GetMapping
    public List<User> find(String name) throws Exception {
        return this.userSrv.find();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException {
        this.userSrv.findByIdAndDelete(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findByIdAndUpdate(UUID id, EditUserDTO body, BindingResult validation) throws Exception {
        if(validation.hasErrors()) throw new BadRequestException(validation.toString());
        return this.userSrv.findByIdAndUpdate(id, body);
    }

    @Override
    public User create(NewUserDTO body, BindingResult validation) throws BadRequestException, IOException {
        return null;
    }

    @PutMapping("/{id}/follow/{followId}")
    public User follow(@PathVariable UUID id, @PathVariable UUID followId) throws ElementNotFoundException {
        return this.userSrv.addFollower(id, followId);
    }

}
