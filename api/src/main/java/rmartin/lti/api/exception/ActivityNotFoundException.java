package rmartin.lti.api.exception;

/**
 * Thrown when the source LMS asks for an activity not registered in the server
 */
public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException(String message) {
        super(message);
    }
}
