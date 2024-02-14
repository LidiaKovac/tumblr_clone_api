package tumblr.api.tumblr_api.exceptions;

import java.util.UUID;


import lombok.AllArgsConstructor;

import java.util.UUID;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String name) {
        super(String.valueOf(name));
    }
}