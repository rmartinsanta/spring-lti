package rmartin.lti.demo_plugin.lti_api;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import rmartin.lti.api.model.LTIContext;

@Service
public class ContextService {

    private static final Logger log = Logger.getLogger(APIClient.class);
    private final APIClient client;


    public ContextService(APIClient client) {
        this.client = client;
    }

    public LTIContext getContext(String key){
        log.info("Getting context with key: " + key);
        return client.getContext(key);
    }

    /**
     * Saves changes to the config in the DB if neccessary, stores context for later use in the cache.
     * @param context LTIContext
     * @return Context key that can be used to retrieve the context in the future.
     */
    public String storeContext(LTIContext context, boolean updateConfig){
        log.info("Storing context with id: "+ context.getId());
        return client.storeContext(context, updateConfig);
    }

    /**
     * Saves changes to the config in the DB if neccessary, stores context for later use in the cache.
     * @param context LTIContext
     * @return Context key that can be used to retrieve the context in the future.
     */
    public String storeContext(LTIContext context){
        log.info("Storing context with id: "+ context.getId());
        return client.storeContext(context, false);
    }
}
