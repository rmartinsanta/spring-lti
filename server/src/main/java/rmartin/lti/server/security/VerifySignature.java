package rmartin.lti.server.security;


import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
// TODO probar si se puede utilizar la interfaz en vez de la implementacion
@PreAuthorize("@requestValidatorImplementation.isValidRequest()")
@Documented
public @interface VerifySignature {}