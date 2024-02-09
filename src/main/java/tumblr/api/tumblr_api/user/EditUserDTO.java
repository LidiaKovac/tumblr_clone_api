package tumblr.api.tumblr_api.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EditUserDTO(
        @Size(min = 10, max = 45, message = "Name must be min 10 char and max 45 char")
        String name,
        @Email
        String email,
        @Size(min = 1, max = 120, message = "Blog Title must be min 1 and max 120 chars")
        String blogTitle,
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[\\W_]).{8,}$", message = "Enter a password with at least one uppercase letter, a symbol and at least 8 characters")
        String password,
        String avatar
) {
}
