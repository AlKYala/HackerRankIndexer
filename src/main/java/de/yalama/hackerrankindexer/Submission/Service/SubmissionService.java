package de.yalama.hackerrankindexer.Submission.Service;

import de.yalama.hackerrankindexer.Submission.Model.FilterRequest;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class SubmissionService implements BaseService<Submission> {

    public abstract Collection<Submission> getSubmissionsFromIDs(Collection<Long> submissions);

    public abstract List<Submission> getAllPassed(UserData userData);
    public abstract List<Submission> getLastPassedFromAll(UserData userData);
    public abstract List<Submission> getAllFailed(UserData userData);

    public abstract Collection<Submission> getByFilterRequest(FilterRequest filterRequest, UserData userData);

    public abstract Collection<Submission> findAllByUserData(UserData userData);

    /**
     * A method that finds a challenge by userDataId and challenge Id
     * @param challengeId The challengeId
     * @param userData The userId
     * @return A challenge instance (can be null)
     */
    public abstract List<Submission> getSubmissionsByChallengeIdAndUserDataId(Long challengeId, UserData userData);
}
