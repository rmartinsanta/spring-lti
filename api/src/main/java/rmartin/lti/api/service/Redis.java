package rmartin.lti.api.service;

import rmartin.lti.api.model.LTIContext;

public interface Redis {
    LTIContext getLTIContext(String id);

    void saveForLaunch(LTIContext context, String key);

    void saveForClient(LTIContext context, String key);

}
