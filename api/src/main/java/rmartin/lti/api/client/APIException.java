package rmartin.lti.api.client;

/**
 * API Error while communicating with the LTI Proxy
 */
public class APIException extends RuntimeException {
    public APIException(Object o) {
        super(o.toString());
    }
}
