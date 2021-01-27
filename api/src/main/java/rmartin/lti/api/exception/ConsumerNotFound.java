package rmartin.lti.api.exception;

/**
 * Thrown when the source LMS asks for an activity not registered in the server
 */
public class ConsumerNotFound extends RuntimeException {
    public ConsumerNotFound(String message) {
        super(message);
    }
}
