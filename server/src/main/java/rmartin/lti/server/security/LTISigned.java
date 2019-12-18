package rmartin.lti.server.security;


import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@requestValidatorImplementation.isValidRequest(#request)")
@Documented
public @interface LTISigned { }