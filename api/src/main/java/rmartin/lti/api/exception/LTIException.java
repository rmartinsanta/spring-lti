package rmartin.lti.api.exception;

/**
 * Thrown when the given source LMS does not have authorization to launch the required activity
 */
public class LTIException extends RuntimeException {
    public LTIException(String message) {
        super(message);
    }
}
