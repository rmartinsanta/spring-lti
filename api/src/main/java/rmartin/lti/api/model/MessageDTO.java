package rmartin.lti.api.model;

public class MessageDTO {

    private LTIContext context;
    private double score;

    protected MessageDTO() {  }

    public MessageDTO(LTIContext context, double score) {
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

    @Override
    public String toString() {
        return "MessageDTO{" +
                "context=" + context.getId() +
                ", score=" + score +
                '}';
    }
}
