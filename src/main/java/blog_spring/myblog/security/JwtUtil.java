package blog_spring.myblog.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    // Secret key for signing JWT
    private static final String SECRET = "67cfdd8b-212c-800c-965c-b13593a4d373-67cfdd8b-212c-800c-965c-b13593a4d373-67cfdd8b-212c-800c-965c-b13593a4d373";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    // private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long EXPIRATION_TIME = 30L * 24L * 60L * 60L * 1000L; // Token Validity: 1 month

    public String generateToken(String email, String userid) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        return Jwts.builder()
            .setClaims(Map.of("email", email, "userid", userid)) // Store both email and id as claims
            .setIssuedAt(new Date()) // Token issued time
            .setExpiration(expirationDate)
            .signWith(secretKey) // Sign with secret key
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            System.out.println(e);
            return false;
        }
    }

    public String extractEmail(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJwt(token)
            .getBody();
        return claims.get("email", String.class);
        // return Jwts.parserBuilder()
        //     .setSigningKey(secretKey)
        //     .build()
        //     .parseClaimsJws(token)
        //     .getBody()
        //     .getSubject();
    }

    public String extractUserid(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.get("userid", String.class); 
    }
}
