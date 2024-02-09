package tumblr.api.tumblr_api.services;

import tumblr.api.tumblr_api.exceptions.BadRequestException;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IService<T, D, B> {
/*
  * T = The class on which the Service will be using (example: Post, User)
  * D = The DTO for newly created entities
  * B= The DTO for editing entities
  *
  * Implementation example:
  * public class UserService implements IService<User, NewUserDTO, EditUserDTO> {
*/
    public T save(D obj) throws BadRequestException;
    public T findById(UUID id) throws ElementNotFoundException;
    public List<T> find();
    public T findByIdAndUpdate(UUID id, B obj) throws Exception;
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException;
}

