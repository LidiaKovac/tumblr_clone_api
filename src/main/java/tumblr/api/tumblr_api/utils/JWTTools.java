package tumblr.api.tumblr_api.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tumblr.api.tumblr_api.exceptions.UnauthorizedException;
import tumblr.api.tumblr_api.user.User;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${spring.jwt.secret}")
    String secret;

    public String createToken(User u) {
        String token = Jwts.builder().setSubject(u.getId().toString()) // A chi appartiene il token (Subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Quando è stato emesso il token (IAT - Issued At)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // Quando scadrà il
                // token
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Genero la firma del token
                .compact(); // Crea il token con quanto detto prima
        return token;
    }

    public void verifyToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnauthorizedException("Token invalid! " + e.getMessage());
        }
    }

    public String decodeToken(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token)
                .getBody().getSubject();
    }

}
