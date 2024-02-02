package tumblr.api.tumblr_api.services;

import org.apache.coyote.BadRequestException;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;
import tumblr.api.tumblr_api.post.Post;
import tumblr.api.tumblr_api.post.PostDTO;

import java.util.List;
import java.util.UUID;

public interface IService<T, D> {
    public T save(D obj) throws BadRequestException;
    public T findById(UUID id) throws ElementNotFoundException;
    public List<T> find();
    public T findByIdAndUpdate(UUID id, D obj) throws Exception;
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException;
}

