package rmartin.lti.api.model;

public class LTIContextUpdateResponse {

    String secret;

    protected LTIContextUpdateResponse() {
    }

    public LTIContextUpdateResponse(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
}
