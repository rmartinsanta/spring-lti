package rmartin.lti.server.service.impls;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import rmartin.lti.api.model.ActivityConfig;
import rmartin.lti.api.model.LTIContext;
import rmartin.lti.api.service.ConfigService;
import rmartin.lti.server.service.repos.ActivityConfigRepository;

@Service
public class ConfigServiceImpl implements ConfigService {

    private static final Logger log = Logger.getLogger(ConfigServiceImpl.class);
    private final ActivityConfigRepository configRepository;

    public ConfigServiceImpl(ActivityConfigRepository configRepository) {
        this.configRepository = configRepository;
    }


    @Override
    public void calculateConfig(LTIContext context) {

        var defaultConfig = configRepository.findByClientIdAndActivityProviderIdAndGlobalIsTrue(context.getClient(), context.getActivityProviderName());
        var contextConfig = context.getConfig();
        if(contextConfig == null){
            contextConfig = new ActivityConfig(context.getClient(), context.getActivityProviderName(), false);
        }

        var contextConfigMap = contextConfig.getConfig();

        if(defaultConfig == null){
            log.info(String.format("Null default config for client %s, activity %s", context.getClient(), context.getActivityProviderName()));
        } else {
            // Merge defaults with current config
            for(var e: defaultConfig.getConfig().entrySet()){
                contextConfigMap.putIfAbsent(e.getKey(), e.getValue());
            }
            log.debug("Merged config: " + contextConfig);
        }

        context.setConfig(contextConfig);
    }

    @Override
    public void update(ActivityConfig c) {
        log.info(String.format("Updating config info for context: %s, global: %s", c.getActivityProviderId(), c.isGlobal()));
        configRepository.save(c);
    }
}
