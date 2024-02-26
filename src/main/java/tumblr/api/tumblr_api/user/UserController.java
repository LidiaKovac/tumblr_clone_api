package tumblr.api.tumblr_api.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public User getMe(@AuthenticationPrincipal User user) {
        return this.userSrv.findById(user.getId());
    }
    @ResponseStatus(HttpStatus.OK)
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

    @GetMapping("/me/following")
    public List<User> getFollowing(@AuthenticationPrincipal User user) throws ElementNotFoundException {
        User found = this.userSrv.findById(user.getId());
        return found.getFollowing();
    }

    @GetMapping("/me/followers")
    public List<User> getFollowers(@AuthenticationPrincipal User user) throws ElementNotFoundException {
        User found = this.userSrv.findById(user.getId());
        return found.getFollowers();
    }

    @GetMapping("/{id}/following")
    public List<User> getFollowingById(@PathVariable UUID id) throws ElementNotFoundException {
        User found = this.userSrv.findById(id);
        return found.getFollowing();
    }

    @GetMapping("/suggestions")
    public List<User> getSuggested(@AuthenticationPrincipal User user) {
//        Remove already followed
        List<UUID> followingIds = user.getFollowing().stream().map(u -> u.getId()).toList();
        return this.userSrv.find().stream().filter(u -> !followingIds.contains(u.getId()) && !u.getId().equals(user.getId())).toList();
    }

    @Override
    @GetMapping
    public List<User> find(String name) throws Exception {
        return this.userSrv.find();
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException {
        this.userSrv.findByIdAndDelete(id);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSelf(@AuthenticationPrincipal User user) throws ElementNotFoundException {
        this.userSrv.findByIdAndDelete(user.getId());
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findByIdAndUpdate(UUID id, EditUserDTO body, BindingResult validation) throws Exception {
        if (validation.hasErrors()) throw new BadRequestException(validation.toString());
        return this.userSrv.findByIdAndUpdate(id, body);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public User updateSelf(@AuthenticationPrincipal User user, @RequestBody EditUserDTO body, BindingResult validation) throws Exception {
        if (validation.hasErrors()) throw new BadRequestException(validation.toString());
        return this.userSrv.findByIdAndUpdate(user.getId(), body);
    }

    @Override
    public User create(NewUserDTO body, BindingResult validation) throws BadRequestException, IOException {
        return null;
    }

    @PutMapping("/follow/{followId}")
    public User follow(@AuthenticationPrincipal User user, @PathVariable UUID followId) throws ElementNotFoundException {
        return this.userSrv.addFollower(user.getId(), followId);
    }

}
