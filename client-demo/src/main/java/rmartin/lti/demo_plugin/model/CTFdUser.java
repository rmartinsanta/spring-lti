package rmartin.lti.demo_plugin.model;

import javax.persistence.*;

@Entity
public class CTFdUser {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int internal_id;

    @Column(unique = true)
    private String email;

    private String password;
    private String name;

    protected CTFdUser(){}

    public CTFdUser(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInternal_id() {
        return internal_id;
    }
}
