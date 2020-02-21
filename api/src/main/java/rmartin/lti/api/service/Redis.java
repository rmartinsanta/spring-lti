package rmartin.lti.api.service;

import rmartin.lti.api.model.LTIContext;

public interface Redis {
    LTIContext getLTIContext(String id);

    void saveLTIContext(LTIContext context, String key);
}
