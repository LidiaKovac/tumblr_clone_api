package tumblr.api.tumblr_api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;
import tumblr.api.tumblr_api.user.User;
import tumblr.api.tumblr_api.user.UserService;
import tumblr.api.tumblr_api.utils.JWTTools;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5137/"})
public class AuthController {

    @Autowired
    UserService srv;

    @Autowired
    JWTTools tools;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @ModelAttribute LoginDTO body) throws ElementNotFoundException {
        User found = this.srv.findByEmail(body.email());
        if(Objects.equals(body.password(), found.getPassword())) {
            return new LoginResponse(tools.createToken(found));
        } else throw new ElementNotFoundException(body.email());
    }
}
