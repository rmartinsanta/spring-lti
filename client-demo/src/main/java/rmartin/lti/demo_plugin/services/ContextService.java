package rmartin.lti.demo_plugin.services;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import rmartin.lti.api.model.LTIContext;
import rmartin.lti.demo_plugin.services.APIClient.APIClient;

import javax.servlet.http.HttpServletRequest;

@Service
public class ContextService {

    private static final Logger log = Logger.getLogger(ContextService.class);
    public static final String SESSION_SECRETKET = "secretkey";
    private final APIClient client;

    private final HttpServletRequest currentRequest;

    public ContextService(APIClient client, HttpServletRequest currentRequest) {
        this.client = client;
        this.currentRequest = currentRequest;
    }

    public LTIContext getContext(){
        String key = (String) currentRequest.getSession().getAttribute(SESSION_SECRETKET);
        log.info("Getting context with key: " + key);
        return client.getContext(key);
    }

    public LTIContext initialize(String id){
        log.info("Getting context with key: " + id);
        var context = client.getContext(id);
        storeContext(context); // Put key in session
        return context;
    }

    /**
     * Saves changes to the config in the DB if neccessary, stores context for later use in the cache.
     * @param context LTIContext
     */
    public void storeContext(LTIContext context, boolean updateConfig){
        log.info("Storing context with id: "+ context.getId());
        String key = client.storeContext(context, updateConfig);
        log.debug(String.format("Updating session: key %s value %s", SESSION_SECRETKET, key));
        currentRequest.getSession().setAttribute(SESSION_SECRETKET, key);
    }

    /**
     * Saves changes to the config in the DB if neccessary, stores context for later use in the cache.
     * @param context LTIContext
     * @return Context key that can be used to retrieve the context in the future.
     */
    public void storeContext(LTIContext context){
        storeContext(context, false);
    }
}
