package tumblr.api.tumblr_api.exceptions;

import java.util.UUID;


import lombok.AllArgsConstructor;

        import java.util.UUID;
public class ElementNotFoundException extends Exception{
    public UUID id;

    public ElementNotFoundException(UUID id) {
        this.id = id;
    }

    public String toString() {
        return "Entity not found, id: " + id;
    }
}