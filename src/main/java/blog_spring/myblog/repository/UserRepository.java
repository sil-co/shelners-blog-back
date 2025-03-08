package blog_spring.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import blog_spring.myblog.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    // Custom query method (optional)
    User findByEmail(String email);
}
