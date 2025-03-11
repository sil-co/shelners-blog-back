package blog_spring.myblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import blog_spring.myblog.entity.User;
import blog_spring.myblog.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // public List<User> getAllUsers() {
    //     return userRepository.findAll();
    // }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // public User saveUser(User user) {
    //     return userRepository.save(user);
    // }

    public boolean verifyUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) { return false; }

        String dbHashedPassword = user.getPasswordHash();
        boolean isMatch = passwordEncoder.matches(password, dbHashedPassword); 

        return isMatch;
    }
}
