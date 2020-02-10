package rmartin.lti.demo_plugin.lti_api;

public class APIException extends RuntimeException {
    public APIException(Object o) {
        super(o.toString());
    }
}
