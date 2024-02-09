package tumblr.api.tumblr_api.exceptions;

import java.util.UUID;


import lombok.AllArgsConstructor;

import java.util.UUID;

public class ElementNotFoundException extends Exception {
    public ElementNotFoundException(UUID id) {
        super(String.valueOf(id));
    }
}