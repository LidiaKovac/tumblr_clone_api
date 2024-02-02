package tumblr.api.tumblr_api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tumblr.api.tumblr_api.controllers.IController;
import tumblr.api.tumblr_api.exceptions.BadRequestException;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController implements IController<User, UserDTO> {

    @Autowired
    UserService userSrv;

    @Override
    @GetMapping("/{id}")
    public User findById(UUID id) throws ElementNotFoundException {
        return this.userSrv.findById(id);
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
    public User findByIdAndUpdate(UUID id, UserDTO body, BindingResult validation) throws Exception {
        return null;
    }

    @Override
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Validated UserDTO body, BindingResult validation) throws BadRequestException {
        if (validation.hasErrors()) {
            String[] errors = validation.getAllErrors()
                    .stream()
                    .map((err) -> err.getDefaultMessage()).toArray(String[]::new);
            throw new BadRequestException("Ci sono errori nel server: " + Arrays.toString(errors));
        } else return this.userSrv.save(body);
    }
}
