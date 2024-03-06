package tumblr.api.tumblr_api.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tumblr.api.tumblr_api.comments.Comment;
import tumblr.api.tumblr_api.comments.CommentsService;
import tumblr.api.tumblr_api.comments.NewCommentDTO;
import tumblr.api.tumblr_api.controllers.IController;
import tumblr.api.tumblr_api.entities.Note;
import tumblr.api.tumblr_api.exceptions.BadRequestException;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;
import tumblr.api.tumblr_api.images.Image;
import tumblr.api.tumblr_api.images.ImageService;
import tumblr.api.tumblr_api.images.NewImageDTO;
import tumblr.api.tumblr_api.likes.Like;
import tumblr.api.tumblr_api.user.User;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = {"http://localhost:5173/"})

public class PostController implements IController<Post, NewPostDTO, EditPostDTO> {

    @Autowired
    PostService postSrv;

    @Autowired
    CommentsService commSrv;

    @Autowired
    ImageService imgSrv;


    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post findById(UUID id) throws ElementNotFoundException {
        return this.postSrv.findById(id);
    }

    @GetMapping("/blog/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> findByUserId(@PathVariable UUID userId) {
        return this.postSrv.findPostsByUserId(userId);
    }

    @GetMapping("/dashboard/public")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getPopularPosts() {
        return this.postSrv.getSorted();
    }

    @GetMapping("/dashboard")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> findFriendsPosts(@AuthenticationPrincipal User user) {
        List<Post> posts = new ArrayList<Post>();
        List<User> following = user.getFollowing();
        for (User f : following) {
            List<Post> singleUserPosts = this.postSrv.findPostsByUserId(f.getId());

            posts.addAll(singleUserPosts);
        }
        posts.addAll(this.postSrv.findPostsByUserId(user.getId()));
        posts.sort(Comparator.comparing((p) -> p.getCreatedAt()));
        for (Post p : posts) {
            System.out.println(p.getLikes());
            p.setLikedByUser(p.getLikes().stream()
                    .map(like -> like.getUser().getId())
                    .anyMatch(id -> id.equals(user.getId())));
        }
        Collections.reverse(posts);
        return posts;
    }

    @GetMapping("/blog/me")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> findMyPosts(@AuthenticationPrincipal User user) {
        return this.postSrv.findPostsByUserId(user.getId());
    }

    @GetMapping("/tags/{tag}")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> findByTag(@PathVariable String tag) {
        return this.postSrv.findByTag(tag);
    }

    @PostMapping("/{postId}/reblog")
    public Post reblog(@AuthenticationPrincipal User user,
                       @PathVariable UUID postId,
                       @ModelAttribute RebloggedPostDTO body
    ) {
        Post found = this.postSrv.findById(postId);
        this.postSrv.saveAfterNote(found);
        return this.postSrv.saveReblog(
                new RebloggedPostDTO(
                        body.markDownContent(),
                        body.tags(),
                        user.getId(),
                        postId
                )
        );
    }

    @GetMapping("/{postId}/reblog")
    public List<Post> getReblogs(@PathVariable UUID postId) {
//        Post found = this.postSrv.findById(postId);
//        return found.getReblogs();
        return this.postSrv.findReblogsById(postId);
    }

    @PutMapping("/like/{postId}")
    public Post likePost(@PathVariable UUID postId, @AuthenticationPrincipal User user) {
        return this.postSrv.likePost(user, postId);
    }

    @GetMapping("/notes/{postId}")
    public NotesDTO getNotes(@PathVariable UUID postId) {
        Post found = this.postSrv.findById(postId);
        List<Comment> comments = found.getComments();
        List<Like> likes = found.getLikes();
        List<Post> reblogs = found.getReblogs();
        NotesDTO notes = new NotesDTO(
                comments,
                likes,
                reblogs,
                comments.size() + likes.size() + reblogs.size()
        );
        return notes;
    }

    @GetMapping("/comments/{postId}")
    public List<Comment> getComments(@PathVariable UUID postId) {
        Post found = this.postSrv.findById(postId);
        return found.getComments();
    }

    @PostMapping("/comments/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Post addComment(@PathVariable UUID postId, @ModelAttribute @Validated NewCommentDTO body, BindingResult validation, @AuthenticationPrincipal User user) {
        if (validation.hasErrors()) throw new BadRequestException(validation.getAllErrors().toString());
        Post found = this.postSrv.findById(postId);
        this.postSrv.saveAfterNote(found);
        this.commSrv.save(new NewCommentDTO(body.content(), user, found));

        return found;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> find(String name) {
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
    public Post create(NewPostDTO body, BindingResult validation) throws BadRequestException, IOException {
        return null;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public Image upload(MultipartFile image) throws IOException {
        Image saved = this.imgSrv.save(
                new NewImageDTO(this.postSrv.upload(image))
        );

        return saved;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@Validated NewPostDTO body, BindingResult validation, @AuthenticationPrincipal User user) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(String.valueOf(validation.getAllErrors()));
        } else {
            System.out.println(body);
            return this.postSrv.save(new NewPostDTO(
                    body.markDownContent(),
                    body.tags(),
                    user.getId()
            ));
        }
    }


}
