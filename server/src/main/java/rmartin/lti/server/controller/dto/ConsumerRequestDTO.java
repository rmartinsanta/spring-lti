package rmartin.lti.server.controller.dto;

public class ConsumerRequestDTO {

    private String username;

    private String email;

    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid(){
        return
                this.username != null && !this.username.trim().isEmpty() &&
                this.email != null && !this.email.trim().isEmpty() &&
                this.password != null && !this.password.trim().isEmpty();
    }
}
