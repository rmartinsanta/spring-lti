package rmartin.lti.demo_plugin.services.APIClient;

/**
 * API Error while communicating with the LTI Proxy
 */
public class APIException extends RuntimeException {
    public APIException(Object o) {
        super(o.toString());
    }
}
