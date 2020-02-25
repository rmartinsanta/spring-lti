package rmartin.lti.demo_plugin.services.APIClient;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rmartin.lti.api.model.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class APIClient {

    private static final Logger logger = Logger.getLogger(APIClient.class);

    private static final String REGISTER_ACTIVITY_ENDPOINT = "/activity/";
    private static final String SCORE_ENDPOINT = "/score/";
    private static final String GET_CONTEXT_ENDPOINT = "/context/?id={id}&secret={secret}";
    private static final String POST_CONTEXT_ENDPOINT = "/context/";

    @Value("${lti.proxy.url:}")
    private String baseUrl;

    @Value("${lti.activity.secret:}")
    private String secret;

    private RestTemplate client;

    @PostConstruct
    public void initialize(){
        this.baseUrl = this.baseUrl.trim();
        if(baseUrl.isEmpty()){
            throw new IllegalArgumentException("Set 'lti.proxy.url' to the proxy base URL");
        }
        if(this.baseUrl.endsWith("/")){
            this.baseUrl = this.baseUrl.substring(0, this.baseUrl.length() - 1);
        }

        this.secret = this.secret.trim();
        if(this.secret.isEmpty()){
            throw new IllegalArgumentException("Set 'lti.activity.secret' to the appropriate activity secret");
        }
        client = new RestTemplate();
    }

    public RegisterActivityResponse registerActivity(RegisterActivityRequest request){
        request.setSecret(secret);
        logger.info("POST " + baseUrl + REGISTER_ACTIVITY_ENDPOINT + ", data: " + request);
        var response = client.postForEntity(baseUrl + REGISTER_ACTIVITY_ENDPOINT, request, RegisterActivityResponse.class);
        return valid(response);
    }

    public LTIContext getContext(String id){
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("secret", secret);
        var response = client.getForEntity(baseUrl + GET_CONTEXT_ENDPOINT, LTIContext.class, params);
        return valid(response);
    }

    public String storeContext(LTIContext context, boolean updateConfig){
        var contextUpdateRequest = new LTIContextUpdateRequest(context, updateConfig);
        var response = client.postForEntity(baseUrl + POST_CONTEXT_ENDPOINT, contextUpdateRequest, LTIContextUpdateResponse.class);
        return valid(response).getSecret();
    }

    public LTIScoreResponse submitGradeRequest(LTIScoreRequest request){
        request.setSecret(secret);
        var response = client.postForEntity(baseUrl + SCORE_ENDPOINT, request, LTIScoreResponse.class);
        return valid(response);
    }

    private static <T> T valid(ResponseEntity<T> response){
        if(response.getStatusCode() != HttpStatus.OK){
            throw new APIException(response);
        }
        return response.getBody();
    }
}
