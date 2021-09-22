package de.yalama.hackerrankindexer.Submission.Service;

import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.services.BaseService;
import de.yalama.hackerrankindexer.shared.services.BaseUserSpecificService;

import java.util.List;
import java.util.Set;

public abstract class SubmissionService implements BaseService<Submission>, BaseUserSpecificService<Submission> {
    public abstract List<Submission> getAllPassed();
    public abstract List<Submission> getLastPassedFromAll();
}
