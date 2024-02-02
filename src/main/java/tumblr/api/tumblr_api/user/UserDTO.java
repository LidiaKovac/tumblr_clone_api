package tumblr.api.tumblr_api.user;

import jakarta.validation.constraints.*;

public record UserDTO(
        @NotEmpty(message = "Please enter a name")
        @NotNull(message = "Please enter a name")
        String name,
        @NotEmpty(message = "Please add en email")
        @NotNull(message = "Please enter an email")
        @Email
        String email,
        @NotEmpty(message = "Add a blog title")
        @Size(min = 2, message = "Blog Title must be at least 2 characters")
        @NotNull(message = "Please add a blog title")
        String blogTitle,
        @NotEmpty(message = "Enter a password with at least one uppercase letter, a symbol and at least 8 characters")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[\\W_]).{8,}$", message = "Enter a password with at least one uppercase letter, a symbol and at least 8 characters")
        String password,
        String avatar,
        User[] followers,
        User[] following
) {
}
