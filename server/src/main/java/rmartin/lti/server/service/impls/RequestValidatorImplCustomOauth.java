package rmartin.lti.server.service.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import rmartin.lti.api.exception.InvalidCredentialsException;
import rmartin.lti.api.exception.InvalidSignatureException;
import rmartin.lti.server.service.KeyService;
import rmartin.lti.server.service.RequestValidator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.Map;

/**
 * Validate incoming LTI requests oauth signatures
 * Custom implementation based on the instructions provided by Twitter
 * https://developer.twitter.com/en/docs/basics/authentication/oauth-1-0a/creating-a-signature
 * Validation tool: http://lti.tools/oauth/
 */
@Service
public class RequestValidatorImplCustomOauth implements RequestValidator {

    private final static Logger log = Logger.getLogger(RequestValidatorImplCustomOauth.class.getName());
    public static final String OAUTH_SIGNATURE = "oauth_signature";

    private final KeyService keyService;
    private final ObjectMapper mapper;

    public RequestValidatorImplCustomOauth(KeyService keyService, ObjectMapper mapper) {
        this.keyService = keyService;
        this.mapper = mapper;
    }

    /**
     * This method verifies the signed HttpServletRequest
     * @param request the HttpServletRequest that will be verified
     * @param secret the secret to verify the properties with
     * @return the result of the verification, along with contextual
     * information
     */
    private void verifyOauthSignature(HttpServletRequest request, String secret) {
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        var params = request.getParameterMap();
        if(!params.containsKey(OAUTH_SIGNATURE)){
            throw new RuntimeException("Missing signature in request");
        }
        var clientSignatures = params.get(OAUTH_SIGNATURE);
        if(clientSignatures.length != 1 || clientSignatures[0].isEmpty()){
            throw new RuntimeException("Signature key present but empty or multiple values detected");
        }

        var toSign = getSignatureString(method, url, params);
        var signature = getSignature(toSign, secret);
        if(!signature.equals(clientSignatures[0])){
            throw new RuntimeException(String.format("Signature mismatch. Got (%s), calculated (%s)", clientSignatures[0], signature));
        }
    }

    public String getSignatureString(String method, String url, Map<String, String[]> params){
        if(url.contains("https://") && url.contains(":443/")){
            throw new IllegalArgumentException("URL seems to contain both https and port 443");
        }
        if(url.contains("http://") && url.contains(":80/")){
            throw new IllegalArgumentException("URL seems to contain both http and port 80");
        }

        return method.toUpperCase() +
                '&' +
                encode(url) +
                '&' +
                encode(getParamString(params));
    }

    public String getParamString(Map<String, String[]> params) {
        var filteredParams = new ArrayList<KeyValue>();
        for(var e: params.entrySet()){
            String key = e.getKey();
            if(key.equals("oauth_signature")) continue;
            for(var p: e.getValue()){
                filteredParams.add(new KeyValue((encode(key)), encode(p)));
            }
        }

        filteredParams.sort(
                Comparator
                .comparing(KeyValue::getKey)
                .thenComparing(KeyValue::getValue)
        );

        var paramsString = new StringBuilder();
        for (KeyValue kv : filteredParams) {
            paramsString.append(kv.getKey()).append('=').append(kv.getValue()).append('&');
        }
        int lastChar = paramsString.length() -1;
        if(paramsString.charAt(lastChar) == '&'){
            paramsString.deleteCharAt(lastChar);
        }
        return paramsString.toString();
    }

    public String getSignature(String s, String key){
        try {
            key = encode(key) + '&'; // Maaaaaagic
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HMACSHA1");
            Mac mac = Mac.getInstance("HMACSHA1");
            mac.init(signingKey);
            mac.update(s.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(mac.doFinal());

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private String encode(String s){
        return URLEncoder.encode(s, StandardCharsets.UTF_8)
                .replace("+", "%20")
                .replace("*", "%2A")
                .replace("%7E", "~");
    }

    /**
     * Verifies that the current request is signed and the given signature is valid
     * @throws InvalidSignatureException If the signature is not valid for this client consumer key.
     */
    @Override
    public void validateRequest(HttpServletRequest request){
        // 1. Custom verifications
        String consumerKey = request.getParameter("oauth_consumer_key");
        if(consumerKey == null || consumerKey.isEmpty()) {
            throw new InvalidCredentialsException("The request must include the parameter oauth_consumer_key");
        }

        String secret = keyService.getSecretForKey(consumerKey);

        // 2. OAuth verification, throws exceptions when failed
        this.verifyOauthSignature(request, secret);
    }

    @Override
    public boolean isValidRequest(HttpServletRequest request){
        try {
            log.debug("Incoming request: \n"  + mapper.writer().writeValueAsString(request.getParameterMap()));
            validateRequest(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.info("Invalid request: " +e.getCause());
            log.debug(e.toString());
            return false;
        }
        return true;
    }

    private static class KeyValue {
        private final String key, value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        private KeyValue(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
