package rmartin.lti.api.exception;

/**
 * Thrown when the given source LMS does not have authorization to launch the required activity
 */
public class ActivityInsufficientPermissionException extends RuntimeException {
    public ActivityInsufficientPermissionException(String message) {
        super(message);
    }
}
