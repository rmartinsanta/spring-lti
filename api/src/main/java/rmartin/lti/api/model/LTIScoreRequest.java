package rmartin.lti.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LTIScoreRequest {

    @JsonProperty("context")
    private LTIContext context;

    @JsonProperty("score")
    private double score;

    @JsonProperty("secret")
    private String secret;

    protected LTIScoreRequest() {  }

    public LTIScoreRequest(LTIContext context, double score) {
        this.context = context;
        this.score = score;
    }

    public LTIContext getContext() {
        return context;
    }

    public void setContext(LTIContext context) {
        this.context = context;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "LTIScoreRequest{" +
                "context=" + context.getId() +
                ", score=" + score +
                '}';
    }
}
