package blog_spring.myblog.service;

import blog_spring.myblog.entity.Post;
import blog_spring.myblog.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(String id) {
        return postRepository.findById(id);
    }

    // public List<Post> getPostsByUserId(String userId) {
    //     return postRepository.findByUserId(userId);
    // }
    
    public Post createPost(Post post) {
        // Todo: Check User exist here
        return postRepository.save(post);
    }

    public Post updatePost(String id, Post postDetails) {
        // return postRepository.findById(id).map(post -> {
        //     post.setTitle(updatedPost.getTitle());
        //     post.setContent(updatedPost.getContent());
        //     return postRepository.save(post);
        // }).orElseThrow(() -> new RuntimeException("Post not found"));
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post existingPost = post.get();
            existingPost.setTitle(postDetails.getTitle());
            existingPost.setContent(postDetails.getContent());
            return postRepository.save(existingPost);
        }
        return null;
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }
}
