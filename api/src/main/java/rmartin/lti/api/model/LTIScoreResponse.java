package rmartin.lti.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LTIScoreResponse {

    @JsonProperty("is_valid")
    boolean isValid;

    @JsonProperty("reason_failed")
    String reasonFailed;

    @JsonProperty("value")
    double value;

    protected LTIScoreResponse() {}

    /**
     * Score validation failed, score was NOT submitted back to LMS
     * @param value value submitted
     * @param reasonFailed why did it fail
     */
    public LTIScoreResponse(double value, String reasonFailed) {
        this.isValid = false;
        this.reasonFailed = reasonFailed;
        this.value = value;
    }

    /**
     * Score validated succeeded, score was submitted back to LMS
     * @param value value submitted
     */
    public LTIScoreResponse(double value) {
        this.isValid = true;
        this.value = value;
    }

    @Override
    public String toString() {
        return "LTIScoreResponse{" +
                "isValid=" + isValid +
                ", reasonFailed='" + reasonFailed + '\'' +
                ", value=" + value +
                '}';
    }
}
