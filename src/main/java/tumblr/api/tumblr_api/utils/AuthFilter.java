package tumblr.api.tumblr_api.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import tumblr.api.tumblr_api.exceptions.UnauthorizedException;
import tumblr.api.tumblr_api.user.User;
import tumblr.api.tumblr_api.user.UserService;

import java.io.IOException;
import java.util.UUID;

@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    JWTTools tools;

    @Autowired
    UserService srv;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, UnauthorizedException {
        String authToken = request.getHeader("Authorization");
        if (authToken == null || !authToken.startsWith("Bearer ")) {
            throw new UnauthorizedException("Please send token!");
        }
        String cleanToken = authToken.substring(7);

        tools.verifyToken(cleanToken);

        String id = tools.decodeToken(cleanToken);
        User user = this.srv.findById(UUID.fromString(id));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        System.out.println(auth);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response); //next() di express
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        return new AntPathMatcher().match("/auth/**", req.getServletPath()) || new AntPathMatcher().match("/post/dashboard/public", req.getServletPath());
    }
}
