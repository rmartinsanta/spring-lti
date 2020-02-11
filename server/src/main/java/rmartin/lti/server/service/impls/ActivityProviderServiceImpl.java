package rmartin.lti.server.service.impls;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmartin.lti.api.exception.ActivityNotFoundException;
import rmartin.lti.api.model.RegisterActivityRequest;
import rmartin.lti.api.service.SecretService;
import rmartin.lti.server.model.ActivityProvider;
import rmartin.lti.server.service.ActivityProviderService;
import rmartin.lti.server.service.repos.ActivityProviderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityProviderServiceImpl implements ActivityProviderService {

    private static final Logger log = Logger.getLogger(ActivityProviderServiceImpl.class);

    @Autowired
    SecretService secretService;

    @Autowired
    ActivityProviderRepository repository;

    @Override
    public Optional<ActivityProvider> getActivityByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Optional<ActivityProvider> getActivityBySecret(String secret) {
        return repository.findBySecret(secret);
    }

    @Override
    public ActivityProvider createActivityProvider(String name) {
        var secret = secretService.generateSecret();
        return createActivityProvider(name, secret);
    }

    public ActivityProvider createActivityProvider(String name, String secret) {
        var activity = new ActivityProvider(name, secret);
        return repository.save(activity);
    }

    @Override
    public boolean canLaunch(String clientId, String name) {
        // No ACLs implemented yet, return true if activity exists
        return getActivityByName(name).isPresent();
    }

    @Override
    public boolean canLaunch(String clientId, ActivityProvider provider) {
        // No ACLs implemented yet, return true if activity exists
        return provider != null;
    }

    @Override
    public ActivityProvider removeActivity(String name) {
        log.info("Deleting activity with name: " + name);
        var activity = this.repository.findByName(name);
        if (activity.isEmpty()) {
            throw new RuntimeException("Could not find an activitywith name: " + name);
        }
        this.repository.delete(activity.get());
        return activity.get();
    }

    @Override
    public List<ActivityProvider> findAll() {
        log.info("Finding all activity providers");
        return this.repository.findAll();
    }

    @Override
    public void updateActivityProvider(RegisterActivityRequest dto) {
        var posibleActivity = repository.findBySecret(dto.getSecret());
        if (posibleActivity.isEmpty()) {
            throw new ActivityNotFoundException("Cannot find any activity for the provided secret: " + dto.getSecret());
        }
        log.info("Updating activity with secret: " + dto.getSecret());
        var activity = posibleActivity.get();
        activity.setName(dto.getActivityName());
        activity.setUrl(dto.getUrl());
        repository.save(activity);
    }
}
