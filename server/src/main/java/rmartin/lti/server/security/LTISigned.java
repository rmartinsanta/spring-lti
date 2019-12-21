//package rmartin.lti.server.security;
//
//
//import org.springframework.security.access.prepost.PreAuthorize;
//
//import java.lang.annotation.Documented;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//
//@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("@requestValidatorImplCustomOauth.isValidRequest(#request)")
//@Documented
//public @interface LTISigned { }
// TODO: fix @LTISigned now that we have our own Oauth implementation