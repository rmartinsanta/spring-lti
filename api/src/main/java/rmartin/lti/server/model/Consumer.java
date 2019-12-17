package rmartin.lti.server.model;

import rmartin.lti.api.exception.InvalidCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Consumer {

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public static final String ROLE_CONSUMER = "ROLE_CONSUMER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, length = 170)
    private String username;

    private String hashedPassword;

    private String secret;

    private String role = ROLE_CONSUMER;

    protected Consumer() {}

    public Consumer(String username, String password, String secret) {
        this.username = username;
        this.hashedPassword = bCryptPasswordEncoder.encode(password);
        this.secret = secret;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String getHashedPassword() {
        return hashedPassword;
    }

    public String getSecret() {
        return secret;
    }

    // TODO Remove setter
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Consumer changePassword(String newPassword){
        if(!bCryptPasswordEncoder.matches(newPassword, this.getHashedPassword()))
            throw new InvalidCredentialsException();

        this.hashedPassword = bCryptPasswordEncoder.encode(newPassword);

        return this;
    }

    public boolean isValidPassword(String password){
        return bCryptPasswordEncoder.matches(password, this.hashedPassword);
    }

    @Override
    public String toString() {
        return "Consumer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin(){
        return this.role.equals(ROLE_ADMIN);
    }

    public String getRole() {
        return this.role;
    }
}
