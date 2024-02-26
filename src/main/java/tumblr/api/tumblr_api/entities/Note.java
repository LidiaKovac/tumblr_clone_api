package tumblr.api.tumblr_api.entities;

import lombok.Getter;
import lombok.Setter;
import tumblr.api.tumblr_api.likes.Like;

import java.util.Date;

@Getter
@Setter
public class Note {
    Date createdAt;
    NoteType type;
}
