package rmartin.lti.server.model;

public class MessageDTO {

    private LaunchContext context;
    private double score;

    protected MessageDTO() {  }

    public MessageDTO(LaunchContext context, double score) {
        this.context = context;
        this.score = score;
    }

    public LaunchContext getContext() {
        return context;
    }

    public void setContext(LaunchContext context) {
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
