package rmartin.lti.server.service;

import javax.servlet.http.HttpServletRequest;

public interface RequestValidator {
    void validateRequest(HttpServletRequest request);

    boolean isValidRequest(HttpServletRequest request);
}
