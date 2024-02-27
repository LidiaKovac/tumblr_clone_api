package tumblr.api.tumblr_api.post;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import tumblr.api.tumblr_api.comments.Comment;
import tumblr.api.tumblr_api.exceptions.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;
import tumblr.api.tumblr_api.likes.Like;
import tumblr.api.tumblr_api.likes.LikesRepo;
import tumblr.api.tumblr_api.services.IService;
import tumblr.api.tumblr_api.user.User;
import tumblr.api.tumblr_api.user.UserRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostService implements IService<Post, NewPostDTO, EditPostDTO> {

    @Autowired
    PostRepository repo;
    @Autowired
    UserRepository userRepo;

    @Autowired
    LikesRepo likeRepo;
    @Autowired
    Cloudinary cloudinary;

    @Override
    public Post save(NewPostDTO obj) throws BadRequestException {
        User owner = this.userRepo.findById(obj.userId()).orElseThrow(() -> new ElementNotFoundException(obj.userId().toString()));
        if (owner != null) {
            return this.repo.save(new Post(obj.markDownContent(), obj.tags(), owner));
        } else throw new BadRequestException("This post doesn't seem to belong to anybody.");
    }

    public Post saveReblog(RebloggedPostDTO obj) {
        Post original = this.findById(obj.originalPostId());
        User owner = this.userRepo.findById(obj.userId()).orElseThrow(() -> new ElementNotFoundException(obj.userId().toString()));
        return this.repo.save(
                new Post(
                        obj.markDownContent(),
                        obj.tags(),
                        owner,
                        original
                )
        );

    }

    public List<Post> findReblogsById(UUID id) {
        return this.repo.findAllByOriginalPostId(id);
    }

    @Override
    public Post findById(UUID id) throws ElementNotFoundException {
        return this.repo.findById(id).orElseThrow(() -> new ElementNotFoundException(id.toString()));
    }

    public List<Post> findPostsByUserId(UUID id) {
        return this.repo.findByUserId(id);
    }

    public List<Post> findByTag(String tag) {
        return this.repo.findAllByTagsContaining(tag);
    }

    public Post likePost(User user, UUID postId) throws ElementNotFoundException {
        Post found = this.repo.findById(postId).orElseThrow(() -> new ElementNotFoundException(postId.toString()));
        if (!found.getLikes().stream()
                .map(like -> like.getUser().getId())
                .distinct()
                .anyMatch(id -> id.equals(user.getId()))) {
            this.saveAfterNote(found);

//            if you haven't liked the post yet
            found.addLike(this.likeRepo.save(new Like(user, found))
            );
        } else {
            System.out.println("Post already liked, removing");

            Like toRemove = found.getLikes().stream().filter(like -> like.getUser().getId().equals(user.getId()))
                    .findFirst()
                    .orElseThrow(() -> new ElementNotFoundException("Likes for post: " + found.getId()));
            List<Like> newLikes = found.removeLike(toRemove).getLikes();
            found.setLikes(newLikes);
        }
        return this.repo.save(found);
    }

    public Post saveAfterNote(Post post) {
        post.increaseNotes();
        this.repo.save(post);
        return post;
    }

    @Override
    public List<Post> find() {
        return this.repo.findAll();
    }

    public List<Post> findByContent(String query) {
        return this.repo.findByMarkDownContentContainsIgnoreCase(query);
    }

    @Override
    public Post findByIdAndUpdate(UUID id, EditPostDTO obj) throws Exception {
        Post found = this.findById(id);
//        found.setImages(obj.images());
        found.setMarkDownContent(obj.markDownContent());
        this.repo.save(found);
        return found;
    }

    @Override
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException {
        Post found = this.findById(id);
        this.repo.delete(found);
    }

    public String upload(MultipartFile file) throws IOException {
        return (String) this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }


}
