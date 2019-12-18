package rmartin.lti.server.service;

import rmartin.lti.server.model.LTIContext;

public interface Redis {
    LTIContext getDataLaunch(String id);

    void saveForLaunch(LTIContext context, String key);

    void saveForClient(LTIContext context, String key);

    LTIContext getDataClient(String key);
}
