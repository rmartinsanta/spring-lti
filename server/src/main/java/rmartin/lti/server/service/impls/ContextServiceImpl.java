package rmartin.lti.server.service.impls;

import rmartin.lti.api.service.Redis;
import rmartin.lti.server.service.repos.ContextRepository;
import rmartin.lti.api.model.LTILaunchRequest;
import rmartin.lti.api.model.LTIContext;
import rmartin.lti.api.service.ContextService;
import rmartin.lti.api.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ContextServiceImpl implements ContextService {

    private final ContextRepository contextRepository;
    private final SecretService secretService;
    private final Redis redis;

    @Autowired
    public ContextServiceImpl(ContextRepository contextRepository, SecretService secretService, Redis redis) {
        this.contextRepository = contextRepository;
        this.secretService = secretService;
        this.redis = redis;
    }

    public LTIContext getOrInitialize(LTILaunchRequest ltiLaunchRequest, String activityName){
        LTIContext context = contextRepository.findByClientAndUserIdAndRolesAndResourceId(
                ltiLaunchRequest.getOauthConsumerKey(),
                ltiLaunchRequest.getUserId(),
                ltiLaunchRequest.getRoles(),
                ltiLaunchRequest.getContextId()
        );

        String key = secretService.generateSecret();
        ltiLaunchRequest.setPublicId(key);

        // todo revisar la segunda parte de este if, es sospechosa
        if(context != null && !context.getLaunchRequests().contains(ltiLaunchRequest)) {
            context.addLaunchRequest(ltiLaunchRequest);
            context.setModified(Instant.now().toEpochMilli());
        } else {
            context = new LTIContext(ltiLaunchRequest, activityName);
        }

        // Context has been created/update, save latest version to DB before pushing data to Redis
        return this.contextRepository.save(context);
    }

    public String storeInCache(LTIContext c){
        String secretKey = this.secretService.generateSecret();
        this.redis.saveLTIContext(c, secretKey);
        return secretKey;
    }

    @Override
    public String updateAndStoreInCache(LTIContext c) {
        LTIContext updated = this.contextRepository.save(c);
        return this.storeInCache(updated);
    }

    public LTIContext get(String key){
        return redis.getLTIContext(key);
    }
}
