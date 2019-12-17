package rmartin.lti.server.service;

import rmartin.lti.server.model.LaunchContext;

public interface Redis {
    LaunchContext getDataLaunch(String id);

    void saveForLaunch(LaunchContext context, String key);

    void saveForClient(LaunchContext context, String key);

    LaunchContext getDataClient(String key);
}
