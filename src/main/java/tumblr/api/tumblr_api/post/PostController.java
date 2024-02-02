package tumblr.api.tumblr_api.post;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tumblr.api.tumblr_api.controllers.IController;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post")
public class PostController implements IController<Post, PostDTO> {

    @Autowired
    PostService postSrv;

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post findById(UUID id) throws ElementNotFoundException {
        return this.postSrv.findById(id);
    }

    @Override
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> find(String name) throws Exception {
        if (name != null) {
            return this.postSrv.findByContent(name);
        } else {
            return this.postSrv.find();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException {
        this.postSrv.findByIdAndDelete(id);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post findByIdAndUpdate(UUID id, PostDTO body, BindingResult validation) throws Exception {
        if (validation.hasErrors()) throw new BadRequestException(String.valueOf(validation.getAllErrors()));
        return this.postSrv.findByIdAndUpdate(id, body);
    }

    @Override
    @PostMapping
    public Post create(PostDTO body, BindingResult validation) throws BadRequestException {
        if (validation.hasErrors()) {
            throw new BadRequestException(String.valueOf(validation.getAllErrors()));
        } else {
            return this.postSrv.save(body);
        }
    }


}
