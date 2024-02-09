package tumblr.api.tumblr_api.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tumblr.api.tumblr_api.exceptions.BadRequestException;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IController<T, D, B> {
    /*
     * T = The class on which the Service will be using (example: Post, User)
     * D = The DTO for newly created entities
     * B = The DTO for editing entities
     *
     * Implementation example:
     * public class UserController implements IController<User, NewUserDTO, EditUserDTO> {
     */

    /*    I don't add Controller annotations like @GetMapping because those require the class to be a Bean,
          I do, however, add all url params, query params and validation here, since they are all similar in
          all Controllers
      * */
    public T findById(@PathVariable UUID id) throws ElementNotFoundException;

    public List<T> find(@RequestParam(required = false) String name) throws Exception;

    public void findByIdAndDelete(@PathVariable UUID id) throws ElementNotFoundException;

    public T findByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated B body, BindingResult validation) throws Exception;

    public T create(@RequestBody @Validated D body, BindingResult validation) throws BadRequestException, IOException;
}
