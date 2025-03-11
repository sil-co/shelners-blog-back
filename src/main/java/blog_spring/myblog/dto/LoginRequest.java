package blog_spring.myblog.dto;

// this class represents the data format for login requests.
public class LoginRequest {
    private String email;
    private String password;

    // Getter and setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
