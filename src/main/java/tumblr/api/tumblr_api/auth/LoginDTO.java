package tumblr.api.tumblr_api.auth;

public record LoginDTO(
        String email,
        String password
) {
}
