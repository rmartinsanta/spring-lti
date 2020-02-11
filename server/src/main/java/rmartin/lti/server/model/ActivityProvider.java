package rmartin.lti.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ActivityProvider {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique=true)
    private String name;

    @Column(unique=true)
    private String secret;

    private String url;

    protected ActivityProvider() { }

    public ActivityProvider(String name, String secret) {
        this.name = name;
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public String getSecret() {
        return secret;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }
}
