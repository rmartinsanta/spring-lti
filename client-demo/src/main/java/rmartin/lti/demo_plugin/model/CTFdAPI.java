package rmartin.lti.demo_plugin.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class CTFdAPI {

    private static Logger log = Logger.getLogger(CTFdAPI.class.getName());

    @Value("${ctfd.url}")
    private String ctfdBaseUrl;

    @Value("${ctfd.token}")
    private String token;

    public boolean registerUser(String email, String name, String password){
        String url = ctfdBaseUrl + "/api/v1/users";
        var client = new RestTemplate();
        var request = buildRequest(email, name, password);
        log.info(String.format("--> %s: %s", url, request));
        var response = client.postForEntity(url, request, CreateUserResponse.class);
        log.info(String.format("<-- %s: %s", response.getStatusCode(), response.getBody()));
        return response.getStatusCode() == HttpStatus.OK;
    }

    private HttpEntity<CreateUserRequest> buildRequest(String email, String name, String password){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        var request = new CreateUserRequest();
        request.name = name;
        request.email = email;
        request.password = password;
        HttpEntity<CreateUserRequest> requestEntity = new HttpEntity<>(request, headers);
        return requestEntity;
    }
}
