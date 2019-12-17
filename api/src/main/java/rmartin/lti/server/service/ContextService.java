package rmartin.lti.server.service;

import rmartin.lti.server.model.LTILaunchRequest;
import rmartin.lti.server.model.LaunchContext;

public interface ContextService {

    // TODO javadocs

    LaunchContext getOrInitialize(LTILaunchRequest ltiLaunchRequest);

    String store(LaunchContext c);

    LaunchContext get(String key);
}
