package rmartin.lti.server.service;

public interface RequestValidator {
    void validateRequest();

    boolean isValidRequest();
}
