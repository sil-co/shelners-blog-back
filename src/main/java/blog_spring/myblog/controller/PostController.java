package blog_spring.myblog.controller;

import blog_spring.myblog.entity.Post;
import blog_spring.myblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        try {
            return postService.getAllPosts();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/test")
    public String testPost() {
        return "test post";
    }

    @GetMapping("/{id}")
    public Optional<Post> getPostById(@PathVariable String id) {
        System.out.println("id error");
        Optional<Post> post = postService.getPostById(id);
        System.out.println(post);
        return post;
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
    public Post updatePost(@PathVariable String id, @RequestBody Post post) {
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable String id) {
        postService.deletePost(id);
    }
}
