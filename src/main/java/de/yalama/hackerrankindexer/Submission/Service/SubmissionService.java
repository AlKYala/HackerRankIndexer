package de.yalama.hackerrankindexer.Submission.Service;

import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import java.util.List;
import java.util.Set;

public abstract class SubmissionService implements BaseService<Submission> {
    public abstract List<Submission> getAllPassed(long sessionId);
    public abstract List<Submission> getLastPassedFromAll(long sessionId);
    public abstract List<Submission> findAllBySessionId(long sessionId);
    public abstract List<Submission> filterBySessionId(List<Submission> submissions, long sessionId);
    public abstract List<Submission> filterBySessionIdAndLanguageId(long pLanguageId, long sessionId);
}
