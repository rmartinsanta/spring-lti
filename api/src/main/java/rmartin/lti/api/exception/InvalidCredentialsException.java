package rmartin.lti.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when one of the following occurs:
 *  - The userkey / userid does not exist or could not be found in the DB
 *  - The user password is incorrect when authenticating against the REST API
 *  - TODO complete this list
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(Throwable cause) {
        super(cause);
    }

    public InvalidCredentialsException(){
        super("Invalid credentials");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
