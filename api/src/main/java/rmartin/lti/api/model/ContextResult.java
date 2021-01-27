package rmartin.lti.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class ContextResult {

    @Id
    @GeneratedValue
    private long id;

    private long timestamp;

    private String score;

    protected ContextResult() {}

    public ContextResult(String score) {
        this.timestamp = Instant.now().toEpochMilli();
        this.score = score;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getScore() {
        return this.score;
    }
}
