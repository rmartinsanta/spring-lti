package rmartin.lti.api.exception;

/**
 * Thrown when a request is malformed, such us:
 * - Missing required parameters
 * - Unsigned LTI request
 * - LTI request failed signature verification
 */
public class InvalidSignatureException extends RuntimeException{
    public InvalidSignatureException(String ex){
        super(ex);
    }

    public InvalidSignatureException(String ex, Exception e){
        super(ex, e);
    }
}
