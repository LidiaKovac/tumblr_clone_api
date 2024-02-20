package tumblr.api.tumblr_api.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tumblr.api.tumblr_api.exceptions.ExceptionsFilter;
import tumblr.api.tumblr_api.utils.AuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity //allows to define single endpoint roles
public class SecurityConfig {
    @Autowired
    AuthFilter filter;

    @Autowired
    ExceptionsFilter excHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable());
//      disable automatic login form
        http.formLogin(f -> f.disable());
//      disable sessions
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(excHandler, AuthFilter.class);

//      disable automatic filter chain for specific paths
//        http.authorizeHttpRequests(req -> req.requestMatchers("/user/**").authenticated());
//        http.authorizeHttpRequests(req -> req.requestMatchers("/post/**").authenticated());
        http.authorizeHttpRequests(req -> req.requestMatchers("/**").permitAll());
        return http.build();
    }

    @Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(15);
    }
}
