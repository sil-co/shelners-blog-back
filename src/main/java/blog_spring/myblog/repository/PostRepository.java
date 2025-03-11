package blog_spring.myblog.repository;

import blog_spring.myblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    // Additional custom queries can be defined here if needed
}
