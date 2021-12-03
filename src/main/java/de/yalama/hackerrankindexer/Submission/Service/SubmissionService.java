package de.yalama.hackerrankindexer.Submission.Service;

import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import java.util.List;
import java.util.Set;

public abstract class SubmissionService implements BaseService<Submission> {
    public abstract List<Submission> getAllPassed(String sessionId);
    public abstract List<Submission> getLastPassedFromAll(String sessionId);
    public abstract List<Submission> findAllBySessionId(String sessionId);
    public abstract List<Submission> filterBySessionId(List<Submission> submissions, String sessionId);
    public abstract List<Submission> filterBySessionIdAndLanguageId(long pLanguageId, String sessionId);
}
