//package rmartin.lti.server.service.impls;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import net.oauth.*;
//import net.oauth.server.OAuthServlet;
//import org.jboss.logging.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import rmartin.lti.api.exception.InvalidCredentialsException;
//import rmartin.lti.api.exception.InvalidSignatureException;
//import rmartin.lti.server.service.KeyService;
//import rmartin.lti.server.service.RequestValidator;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * Validate incoming LTI requests oauth signatures
// * Based on: https://raw.githubusercontent.com/IMSGlobal/basiclti-util-java/master/src/main/java/org/imsglobal/lti/launch/LtiOauthVerifier.java
// */
////@Service
//public class RequestValidatorImplementation implements RequestValidator {
//
//    private final static Logger log = Logger.getLogger(RequestValidatorImplementation.class.getName());
//
//    @Autowired
//    private KeyService keyService;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    /**
//     * This method verifies the signed HttpServletRequest
//     * @param request the HttpServletRequest that will be verified
//     * @param secret the secret to verify the properties with
//     * @return the result of the verification, along with contextual
//     * information
//     */
//    private void verifyOauthSignature(HttpServletRequest request, String secret) {
//        OAuthMessage oam = OAuthServlet.getMessage(request, OAuthServlet.getRequestURL(request));
//        String oauth_consumer_key;
//        try {
//            oauth_consumer_key = oam.getConsumerKey();
//        } catch (Exception e) {
//            throw new InvalidSignatureException("Unable to find consumer key in message", e);
//        }
//
//        OAuthValidator oav = new SimpleOAuthValidator();
//        OAuthConsumer cons = new OAuthConsumer(null, oauth_consumer_key, secret, null);
//        OAuthAccessor acc = new OAuthAccessor(cons);
//
//        try {
//            oav.validateMessage(oam, acc);
//        } catch (Exception e) {
//            throw new InvalidSignatureException("Failed to validate", e);
//        }
//    }
//
//    /**
//     * Verifies that the current request is signed and the given signature is valid
//     * @throws InvalidSignatureException If the signature is not valid for this client consumer key.
//     */
//    @Override
//    public void validateRequest(HttpServletRequest request){
//
//        // 1. Custom verifications
//        String consumerKey = request.getParameter("oauth_consumer_key");
//        if(consumerKey == null || consumerKey.isEmpty()) {
//            throw new InvalidCredentialsException("The request must include the parameter oauth_consumer_key");
//        }
//
//        String secret = keyService.getSecretForKey(consumerKey);
//
//        // 2. OAuth verification, throws exceptions when failed
//        this.verifyOauthSignature(request, secret);
//    }
//
//    @Override
//    public boolean isValidRequest(HttpServletRequest request){
//        try {
//            log.info("Incoming request: \n"  + mapper.writer().writeValueAsString(request.getParameterMap()));
//            validateRequest(request);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        } catch (Exception e) {
//            log.info("Invalid request: " +e.getCause());
//            log.debug(e.toString());
//            return false;
//        }
//        return true;
//    }
//}
