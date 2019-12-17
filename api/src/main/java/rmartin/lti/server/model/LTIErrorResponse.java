package rmartin.lti.server.model;

import org.springframework.http.HttpStatus;

public class LTIErrorResponse {
    private HttpStatus responseStatus;
    private RuntimeException exception;

    public LTIErrorResponse(HttpStatus responseStatus, RuntimeException exception) {
        this.responseStatus = responseStatus;
        this.exception = exception;
    }

    public HttpStatus getResponseStatus() {
        return responseStatus;
    }

    public RuntimeException getException() {
        return exception;
    }

}
