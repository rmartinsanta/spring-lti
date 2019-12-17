package rmartin.lti.api.exception;

/**
 * Thrown when an error happens while trying to submit the grade to the source LMS,
 * for example when the given score is not valid or in range
 */
public class GradeException extends RuntimeException {
    public GradeException(Throwable cause) {
        super(cause);
    }

    public GradeException(String message) {
        super(message);
    }
}
