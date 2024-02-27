package tumblr.api.tumblr_api.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tumblr.api.tumblr_api.exceptions.BadRequestException;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;
import tumblr.api.tumblr_api.services.IService;

import java.util.List;
import java.util.UUID;

@Service
public class ImageService implements IService<Image, NewImageDTO, NewImageDTO> {
    @Autowired
    ImageRepository repo;

    @Override
    public Image save(NewImageDTO obj) throws BadRequestException {
        return this.repo.save(
                new Image(
                        obj.url()
                )
        );
    }

    @Override
    public Image findById(UUID id) throws ElementNotFoundException {
        return null;
    }

    @Override
    public List<Image> find() {
        return null;
    }

    @Override
    public Image findByIdAndUpdate(UUID id, NewImageDTO obj) throws Exception {
        return null;
    }

    @Override
    public void findByIdAndDelete(UUID id) throws ElementNotFoundException {

    }
}
