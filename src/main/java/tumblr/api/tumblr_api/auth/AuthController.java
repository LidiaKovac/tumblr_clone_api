package tumblr.api.tumblr_api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tumblr.api.tumblr_api.exceptions.BadRequestException;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;
import tumblr.api.tumblr_api.exceptions.UnauthorizedException;
import tumblr.api.tumblr_api.user.NewUserDTO;
import tumblr.api.tumblr_api.user.User;
import tumblr.api.tumblr_api.user.UserService;
import tumblr.api.tumblr_api.utils.JWTTools;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5173/"})

public class AuthController {

    @Autowired
    UserService srv;

    @Autowired
    JWTTools tools;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @ModelAttribute LoginDTO body) throws ElementNotFoundException, UnauthorizedException {
        User found = this.srv.findByEmail(body.email());
        if(found == null) {
            throw new ElementNotFoundException(body.email());
        }
        if(Objects.equals(body.password(), found.getPassword())) {
            return new LoginResponse(tools.createToken(found));
        } else throw new BadRequestException("Wrong password!");
    }

    @PostMapping("/register")
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
            return this.srv.save(created);

        } else {
            System.out.println(body.avatar());
            String url = this.srv.uploadImage(body.avatarFile());
            NewUserDTO created = new NewUserDTO(
                    body.name(),
                    body.email(),
                    body.blogTitle(),
                    body.password(),
                    null,
                    url);
            return this.srv.save(created);
        }

    }
}
