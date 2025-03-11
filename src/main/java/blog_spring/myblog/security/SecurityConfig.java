package blog_spring.myblog.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    // "/api/posts", 
                    // "/api/posts/*", 
                    "/api/users/login", 
                    "/api/posts/verify-owner/**", 
                    "/api/users/test/**"
                ).permitAll() // Make /api/posts public
                // .requestMatchers("/api/posts/**").authenticated() // Require authentication for POST, PUT, DELETE
                .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll() // Allow GET request without authentication
                .requestMatchers(HttpMethod.POST, "/api/posts").permitAll() // Require authentication for creating posts
                .requestMatchers(HttpMethod.PUT, "/api/posts/**").permitAll() // Require authentication for updates
                .anyRequest().authenticated() // Require authentication for other endpoints
            )
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Stateless API
        
        return http.build();
    }

    // Define CORS setting
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}
