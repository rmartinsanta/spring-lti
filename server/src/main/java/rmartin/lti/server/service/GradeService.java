package rmartin.lti.server.service;

import rmartin.lti.api.model.LTIContext;

public interface GradeService {
    void gradeActivity(LTIContext request, String score);
}
