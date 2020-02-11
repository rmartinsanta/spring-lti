package rmartin.lti.server.service.impls;

import org.springframework.stereotype.Service;
import rmartin.lti.api.model.ActivityConfig;
import rmartin.lti.api.model.LTILaunchRequest;
import rmartin.lti.api.service.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Override
    public ActivityConfig getOrInitialize(LTILaunchRequest ltiLaunchRequest) {
        return null;
    }

    @Override
    public void save(ActivityConfig c) {

    }
}
