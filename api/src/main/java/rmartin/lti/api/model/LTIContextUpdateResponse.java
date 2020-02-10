package rmartin.lti.api.model;

public class LTIContextUpdateResponse {

    String secret;

    public LTIContextUpdateResponse(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
}
