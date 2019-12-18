package rmartin.lti.server.service.impls;

import rmartin.lti.server.model.ActivityConfig;
import rmartin.lti.server.model.LTILaunchRequest;
import rmartin.lti.server.service.Redis;
import rmartin.lti.server.service.SecretService;
import rmartin.lti.server.service.repos.ActivityConfigRepository;
import rmartin.lti.server.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    private final ActivityConfigRepository configs;

    private final Redis redis;

    private final SecretService secretGenerator;

    @Autowired
    public ConfigServiceImpl(ActivityConfigRepository repository, Redis redis, SecretService secretGenerator) {
        this.configs = repository;
        this.redis = redis;
        this.secretGenerator = secretGenerator;
    }

    @Override
    public ActivityConfig getOrInitialize(LTILaunchRequest request) {
        ActivityConfig config = configs.findByClientIdAndActivityId(request.getOauthConsumerKey(), request.getActivityId());
        if(config == null){
            config = initialize(request);
        }

        return config;
    }

    private ActivityConfig initialize(LTILaunchRequest request){
        ActivityConfig config = new ActivityConfig(request.getOauthConsumerKey(), request.getActivityId());
        return configs.save(config);
    }

    public void save(ActivityConfig c){
        this.configs.save(c);
    }
}
