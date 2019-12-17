package rmartin.lti.server.service.impls;

import rmartin.lti.server.service.Redis;
import rmartin.lti.server.service.repos.ContextRepository;
import rmartin.lti.server.model.LTILaunchRequest;
import rmartin.lti.server.model.LaunchContext;
import rmartin.lti.server.service.ConfigService;
import rmartin.lti.server.service.ContextService;
import rmartin.lti.server.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ContextServiceImpl implements ContextService {

    private final ContextRepository contextRepository;

    private final SecretService secretService;

    private final ConfigService configService;

    private final Redis redis;

    @Autowired
    public ContextServiceImpl(ContextRepository contextRepository, SecretService secretService, ConfigService configService, Redis redis) {
        this.contextRepository = contextRepository;
        this.secretService = secretService;
        this.configService = configService;
        this.redis = redis;
    }

    public LaunchContext getOrInitialize(LTILaunchRequest ltiLaunchRequest){
        LaunchContext context = contextRepository.findByClientAndUserIdAndRolesAndResourceId(
                ltiLaunchRequest.getOauthConsumerKey(),
                ltiLaunchRequest.getUserId(),
                ltiLaunchRequest.getRoles(),
                ltiLaunchRequest.getContextId()
        );

        String key = secretService.generateSecret();
        ltiLaunchRequest.setPublicId(key);

        if(context != null) {
            context.addLaunchRequest(ltiLaunchRequest);
            context.setModified(Instant.now().toEpochMilli());

        } else {
            context = new LaunchContext(ltiLaunchRequest);
        }

        context.setConfig(configService.getOrInitialize(ltiLaunchRequest));

        return this.contextRepository.save(context);
    }

    public String store(LaunchContext c){
        String secretKey = this.secretService.generateSecret();
        this.redis.saveForClient(c, secretKey);
        return secretKey;
    }

    public LaunchContext get(String key){
        return redis.getDataClient(key);
    }
}
