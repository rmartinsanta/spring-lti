package rmartin.lti.demo_plugin.model;

/**
 * DTO tontito para crear los usuarios segun vaya siendo necesario en CTFd,
 * con todos los campos que podria devoler en la respuesta.
 * El uso de Integer/Boolean es necesario porque no esta documentado que campos son opcionales.
 */
public class CreateUserRequest {
    public Integer id;
    public Integer oauth_id;
    public String name;
    public String password;
    public String email;
    public String type;
    public String secret;
    public String affiliation;
    public String country;
    public String bracket;
    public Boolean hidden;
    public Boolean banned;
    public Boolean verified;
    public Integer team_id;
    public String created;
}
