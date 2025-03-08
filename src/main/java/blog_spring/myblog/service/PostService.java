package blog_spring.myblog.service;

import blog_spring.myblog.entity.Post;
import blog_spring.myblog.entity.User;
import blog_spring.myblog.repository.PostRepository;
import blog_spring.myblog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(String id) {
        return postRepository.findById(id);
    }
    
    public List<Post> getPostsByUserId(String userId) {
        return postRepository.findByUserId(userId);
    }
    
    public Post createPost(String userId, Post post) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        return postRepository.save(post);
    }

    public Post updatePost(String id, Post updatedPost) {
        return postRepository.findById(id).map(post -> {
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            return postRepository.save(post);
        }).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }
}
