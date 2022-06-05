package de.yalama.hackerrankindexer.SubmissionFlat.Service;

import de.yalama.hackerrankindexer.SubmissionFlat.Model.SubmissionFlat;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.services.BaseService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class SubmissionFlatService implements BaseService<SubmissionFlat> {
    /**
     * Returns a collection of submissions by matching Ids
     * @param submissionIDs The collection of submissions
     * @return a collection of submissions by matching Ids
     */
    public abstract Collection<SubmissionFlat> getSubmissionsFromIDs(Collection<Long> submissionIDs);

    /**
     * Gets all passed submissions found in userData
     * @param userData The user Data instance (parent element of submission)
     * @return All passed submissions of user data
     */
    public abstract List<SubmissionFlat> getAllPassed(UserData userData);

    /**
     * Finds all submissions by User Data
     * @param userDataId the Id of the user Data instance
     * @return all submissions of userdata id in parameter
     */
    public abstract List<SubmissionFlat> findAllByUserDataId(Long userDataId);

    /**
     * For every passed challenge, this function returns the submission that has the highest id and is passed
     * @param userData The user Data instance (parent element of submission)
     * @return All passed submissions of user data
     */
    public abstract List<SubmissionFlat> getLastPassedFromAll(UserData userData);

    /**
     * Gets all failed submissions found in userData
     * @param userData The user Data instance (parent element of submission)
     * @return All failed submissions of user data
     */
    public abstract List<SubmissionFlat> getAllFailed(UserData userData);

    /**
     * A method that finds a challenge by userDataId and challenge Id
     * @param challengeId The challengeId
     * @param userDataId The userData Id
     * @return A challenge instance (can be null)
     */
    public abstract List<SubmissionFlat> getSubmissionsByChallengeIdAndUserDataId(Long challengeId, Long userDataId);

    /**
     * @return all Submission instances found by given pLanguage ids and passed userDataId
     */
    public abstract Set<SubmissionFlat> getSubmissionsByLanguagesAndUserDataId(@RequestBody List<Long> pLanguageIds, Long userDataId);
}
