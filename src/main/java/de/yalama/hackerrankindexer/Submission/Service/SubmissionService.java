package de.yalama.hackerrankindexer.Submission.Service;

import de.yalama.hackerrankindexer.Submission.Model.FilterRequest;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.services.BaseService;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class SubmissionService implements BaseService<Submission> {

    /**
     * Returns a collection of submissions by matching Ids
     * @param submissionIDs The collection of submissions
     * @return a collection of submissions by matching Ids
     */
    public abstract Collection<Submission> getSubmissionsFromIDs(Collection<Long> submissionIDs);

    /**
     * Gets all passed submissions found in userData
     * @param userData The user Data instance (parent element of submission)
     * @return All passed submissions of user data
     */
    public abstract List<Submission> getAllPassed(UserData userData);

    /**
     * Finds all submissions by User Data
     * @param userDataId the Id of the user Data instance
     * @return all submissions of userdata id in parameter
     */
    public abstract List<Submission> findAllByUserDataId(Long userDataId);

    /**
     * For every passed challenge, this function returns the submission that has the highest id and is passed
     * @param userData The user Data instance (parent element of submission)
     * @return All passed submissions of user data
     */
    public abstract List<Submission> getLastPassedFromAll(UserData userData);

    /**
     * Gets all failed submissions found in userData
     * @param userData The user Data instance (parent element of submission)
     * @return All failed submissions of user data
     */
    public abstract List<Submission> getAllFailed(UserData userData);

    /**
     * A method that finds a challenge by userDataId and challenge Id
     * @param challengeId The challengeId
     * @param userDataId The userData Id
     * @return A challenge instance (can be null)
     */
    public abstract List<Submission> getSubmissionsByChallengeIdAndUserDataId(Long challengeId, Long userDataId);

    /**
     * @return all Submission instances found by given pLanguage ids and passed userDataId
     */
    public abstract Set<Submission> getSubmissionsByLanguagesAndUserDataId(@RequestBody List<Long> pLanguageIds, Long userDataId);
}
