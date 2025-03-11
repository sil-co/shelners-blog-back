package blog_spring.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import blog_spring.myblog.dto.LoginRequest;
import blog_spring.myblog.entity.User;
import blog_spring.myblog.security.JwtUtil;
import blog_spring.myblog.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController // Marks this class as a REST API controller
@RequestMapping("/api/users") // Base URL for all user-related endpoints.
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil; // Inject JWT Utility

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Login endpoint - handles user authentication
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        System.out.println("error login post");
        boolean isValid = userService.verifyUser(request.getEmail(), request.getPassword());
        if (isValid) {
            User user = userService.getUserByEmail(request.getEmail());
            String token = jwtUtil.generateToken(request.getEmail(), user.getId());
            return ResponseEntity.ok(token); // 200 OK response if credentials are valid.
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"); // 401 Unauthorized
        }
    }

    @GetMapping("/auth")
    public ResponseEntity<Map<String, String>> checkCurrentUser(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (jwtUtil.validateToken(token)) {
            String email = jwtUtil.extractEmail(token);
            Map<String, String> response = new HashMap<>();
            response.put("email", email);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping("/test/{password}")
    public String testHash(@PathVariable String password) {
        System.out.println(password);
        String passwordHash = passwordEncoder.encode(password);
        System.out.println(passwordHash);
        return new String();
    }
    
    // @GetMapping
    // public List<User> getAllUsers() {
    //     return userService.getAllUsers();
    // }

    // @PostMapping
    // public User createUser(@RequestBody User user) {
    //     return userService.saveUser(user);
    // }

    // @GetMapping("/{email}")
    // public User getUserByEmail(@PathVariable String email) {
    //     return userService.getUserByEmail(email);
    // }
}
