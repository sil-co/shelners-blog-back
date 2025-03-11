package blog_spring.myblog.controller;

import blog_spring.myblog.entity.Post;
import blog_spring.myblog.repository.PostRepository;
import blog_spring.myblog.security.JwtUtil;
import blog_spring.myblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public List<Post> getAllPosts() {
        try {
            return postService.getAllPosts();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/{id}")
    public Optional<Post> getPostById(@PathVariable String id) {
        Optional<Post> post = postService.getPostById(id);
        return post;
    }

    @GetMapping("/verify-owner/{id}")
    public ResponseEntity<Map<String, Boolean>> CheckPostOwnership(
        @PathVariable String id,
        @RequestHeader("Authorization") String token
    ) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("isOwner", false));
        }
        Optional<Post> postOpt = postService.getPostById(id); 
        if (postOpt.isPresent()) {
            String userId = jwtUtil.extractUserid(token);
            String postAuthorId = postOpt.get().getUserId();
            boolean isOwner = userId.equals((postAuthorId));
            return ResponseEntity.ok(Collections.singletonMap("isOwner", isOwner));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("isOwner", false));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestHeader("Authorization") String token) {
        if (!isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Post savedPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    // @GetMapping("/user/{userId}")
    // public List<Post> getPostsByUserId(@PathVariable String userId) {
    //     return postService.getPostsByUserId(userId);
    // }

    // @PostMapping("/user/{userId}")
    // public Post createPost(@PathVariable String userId, @RequestBody Post post) {
    //     return postService.createPost(userId, post);
    // }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post, @RequestHeader("Authorization") String token) {
        System.out.println("error: " + token);
        if (!isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Post updatedPost = postService.updatePost(id, post);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id, @RequestHeader("Authorization") String token) {
        if (!isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // Helper method to validate JWT token
    private boolean isTokenValid(String token) {
        if (token == null || !token.startsWith("Bearer ")) { return false; }
        token = token.substring(7); // Remove "Bearer " prefix
        return jwtUtil.validateToken(token);
    }
}
