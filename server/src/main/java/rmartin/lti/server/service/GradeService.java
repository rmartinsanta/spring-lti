package rmartin.lti.server.service;

import rmartin.lti.server.model.LaunchContext;

public interface GradeService {
    void gradeActivity(LaunchContext request, String score);
}
