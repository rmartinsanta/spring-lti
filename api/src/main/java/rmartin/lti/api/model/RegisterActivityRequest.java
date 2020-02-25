package rmartin.lti.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class RegisterActivityRequest {

    @JsonProperty("activity_name")
    private String activityName;

    @JsonProperty("url")
    private String url;

    @JsonProperty("secret")
    private String secret;

    @JsonProperty("default_config")
    private ActivityConfig defaultConfig;

    protected RegisterActivityRequest() { }

    /**
     * Register activity using a specific config.
     * @param activityName our activity name
     * @param url our url, used by the proxy to redirect the requests
     * @param defaultConfig default config
     */
    public RegisterActivityRequest(String activityName, String url, ActivityConfig defaultConfig) {
        this.activityName = activityName;
        this.url = url;
        this.defaultConfig = defaultConfig;
    }

    /**
     * Register activity DTO. Uses empty config.
     * @param activityName our name
     * @param url our url, used by the proxy to redirect the requests
     */
    public RegisterActivityRequest(String activityName, String url){
        this(activityName, url, new ActivityConfig(activityName));
    }

    public String getActivityName() {
        return activityName;
    }

    public String getUrl() {
        return url;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isValid(){
        return      !Objects.requireNonNull(activityName).trim().isEmpty()
                &&  !Objects.requireNonNull(url).trim().isEmpty()
                &&  !Objects.requireNonNull(secret).trim().isEmpty();
    }

    @Override
    public String toString() {
        return "RegisterActivityRequest{" +
                "activityName='" + activityName + '\'' +
                ", url='" + url + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
