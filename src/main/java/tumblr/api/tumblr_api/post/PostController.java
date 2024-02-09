package tumblr.api.tumblr_api.post;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tumblr.api.tumblr_api.controllers.IController;
import tumblr.api.tumblr_api.exceptions.BadRequestException;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;
import tumblr.api.tumblr_api.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = {"http://localhost:5173/"})

public class PostController implements IController<Post, NewPostDTO, EditPostDTO> {

    @Autowired
    PostService postSrv;

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post findById(UUID id) throws ElementNotFoundException {
        return this.postSrv.findById(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> find(String name){
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
    public Post findByIdAndUpdate(UUID id, EditPostDTO body, BindingResult validation) throws Exception {
        if (validation.hasErrors()) throw new BadRequestException(String.valueOf(validation.getAllErrors()));
        return this.postSrv.findByIdAndUpdate(id, body);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@ModelAttribute NewPostDTO body, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(String.valueOf(validation.getAllErrors()));
        } else {
            System.out.println(body);
            List<String> imageUrls = new ArrayList<>();
            for (int i = 0; i < body.imageFiles().size(); i++) {
                imageUrls.add(this.postSrv.upload(body.imageFiles().get(i)));
            }
            return this.postSrv.save(new NewPostDTO(
                    body.markDownContent(),
                    null,
                    imageUrls,
                    body.tags(),
                    body.userEmail()
            ));
        }
    }


}
