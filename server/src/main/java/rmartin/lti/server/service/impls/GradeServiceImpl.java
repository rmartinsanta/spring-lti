package rmartin.lti.server.service.impls;

import rmartin.lti.api.exception.GradeException;
import rmartin.lti.server.service.KeyService;
import rmartin.lti.server.service.repos.ContextRepository;
import rmartin.lti.api.model.ContextResult;
import rmartin.lti.api.model.LTILaunchRequest;
import rmartin.lti.api.model.LTIContext;
import rmartin.lti.server.service.GradeService;
import oauth.signpost.exception.OAuthException;
import org.imsglobal.pox.IMSPOXRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class GradeServiceImpl implements GradeService {

    private final KeyService keyService;
    private final ContextRepository contextRepository;

    @Autowired
    public GradeServiceImpl(KeyService keyService, ContextRepository contextRepository) {
        this.keyService = keyService;
        this.contextRepository = contextRepository;
    }

    public void gradeActivity(LTIContext context, String score) {
        LTILaunchRequest lastRequest = context.getLastRequest();
        String consumerKey = lastRequest.getOauthConsumerKey();
        String secretKey = keyService.getSecretForKey(consumerKey);

        try {
            IMSPOXRequest.sendReplaceResult(lastRequest.getOutcomeURL(), consumerKey, secretKey, lastRequest.getResultSourceId(), score);
            context.addResult(new ContextResult(score));
            contextRepository.save(context);
        }
        catch (IOException | OAuthException | GeneralSecurityException e){
            throw new GradeException(e);
        }
    }
}
