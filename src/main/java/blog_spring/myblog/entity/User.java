package blog_spring.myblog.entity;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Maps to the "users" table in MySQL
public class User {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    } 

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
