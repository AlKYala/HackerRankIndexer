package de.yalama.hackerrankindexer.Submission.Service;

import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class SubmissionService implements BaseService<Submission> {
    public abstract List<Submission> getAllPassed(User user);
    public abstract Collection<Submission> getLastPassedFromAll(User user);

    public abstract Collection<Submission> findAllByUser(User user);
}
