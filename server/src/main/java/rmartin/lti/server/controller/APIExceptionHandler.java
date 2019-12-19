package rmartin.lti.server.controller;

import rmartin.lti.api.exception.*;
import rmartin.lti.api.model.LTIErrorResponse;
import org.jboss.logging.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@ResponseBody
public class APIExceptionHandler {

    private Logger log = Logger.getLogger(APIExceptionHandler.class);

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<LTIErrorResponse> invalidCredentialsHandler(InvalidCredentialsException e){
        return logAndRespond(HttpStatus.UNAUTHORIZED, e, "Invalid Credentials");
    }

    @ExceptionHandler(InvalidSignatureException.class)
    public ResponseEntity<LTIErrorResponse> invalidSignatureHandler(InvalidSignatureException e){
        return logAndRespond(HttpStatus.BAD_REQUEST, e, "Invalid signature");
    }

    @ExceptionHandler(ActivityNotFoundException.class)
    public ResponseEntity<LTIErrorResponse> activityNotFound(ActivityNotFoundException e){
        return logAndRespond(HttpStatus.NOT_FOUND, e, "Activity not found");
    }

    @ExceptionHandler(ActivityInsufficientPermissionException.class)
    public ResponseEntity<LTIErrorResponse> activityInsufficientPerm(ActivityNotFoundException e){
        return logAndRespond(HttpStatus.FORBIDDEN, e, "You do not have permissions to launch that activity");
    }

    @ExceptionHandler(GradeException.class)
    public ResponseEntity<LTIErrorResponse> gradeException(GradeException e){
        return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e, "An error ocurred while trying to grade the activity");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<LTIErrorResponse> dtoError(IllegalArgumentException e){
        return logAndRespond(HttpStatus.BAD_REQUEST, e, "Invalid request");
    }

    private ResponseEntity<LTIErrorResponse> logAndRespond(HttpStatus s, RuntimeException e, String m){
        log.info(s.value() + " - Captured exception: " + e.getMessage());
        log.debug(e.toString());
        return new ResponseEntity<>(new LTIErrorResponse(s, e), s);
    }

}
