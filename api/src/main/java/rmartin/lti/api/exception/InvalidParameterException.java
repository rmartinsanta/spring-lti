package rmartin.lti.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(Throwable cause) {
        super(cause);
    }

    public InvalidParameterException(String message) {
        super(message);
    }
}
