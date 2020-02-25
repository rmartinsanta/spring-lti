package rmartin.lti.server.service.impls;

import org.jboss.logging.Logger;
import org.springframework.security.access.AccessDeniedException;
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

        var defaultConfig = configRepository.findByActivityProviderIdAndGlobalIsTrue(context.getActivityProviderName());
        ActivityConfig currentConfig = getActivityConfig(context);

        var contextConfigMap = currentConfig.getConfigMap();

        if(defaultConfig == null){
            log.info(String.format("Null default config for client %s, activity %s", context.getClient(), context.getActivityProviderName()));
        } else {
            // Merge defaults with current config
            for(var e: defaultConfig.getConfigMap().entrySet()){
                contextConfigMap.putIfAbsent(e.getKey(), e.getValue());
            }
            log.debug("Merged config: " + currentConfig);
        }

        context.setConfig(currentConfig);
    }

    private ActivityConfig getActivityConfig(LTIContext context) {
        return configRepository.findByActivityProviderIdAndClientIdAndResourceIdAndGlobalIsFalse(context.getActivityProviderName(), context.getClient(), context.getResourceId())
                    .orElseGet(() -> new ActivityConfig(context.getClient(), context.getActivityProviderName(), context.getResourceId()));
    }


    /**
     * Persist config changes for a given context.
     * @param context Modified config
     */
    @Override
    public void update(LTIContext context) {
        if(!context.isPrivileged()){
            throw new AccessDeniedException(String.format("Only teachers or admins can update context config: Context id %s - Config: %s", context.getId(), context.getConfig().getSerialized()));
        }

        var currentConfig = getActivityConfig(context);
        for(var entry: context.getConfig().getConfigMap().entrySet()){
            currentConfig.getConfigMap().put(entry.getKey(), entry.getValue());
        }
        log.info(String.format("Updating config info for activity: %s, data: %s", currentConfig.getActivityProviderId(), currentConfig.getSerialized()));
        configRepository.save(currentConfig);
    }
}
