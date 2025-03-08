package blog_spring.myblog.controller;

// Maps HTTP GET requests to methods
import org.springframework.web.bind.annotation.GetMapping;
// Defines the base URL path for the controller
import org.springframework.web.bind.annotation.RequestMapping;
// Marks the class as a REST controller, returning JSON or text responses.
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/api-test") 
public class MyFirstAPI {
    @GetMapping("/hello") 
    public String SayHello() {
        return "Hello, Spring Boot!";
    }
}
