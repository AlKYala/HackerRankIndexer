package de.yalama.hackerrankindexer.Challenge.Service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import java.util.List;
import java.util.Set;

public abstract class ChallengeService implements BaseService<Challenge> {

    /**
     * A method that finds a challenge by userDataId and challenge Id
     * @param challengeId The challengeId
     * @param userData The userId
     * @return A challenge instance (can be null)
     */
    public abstract List<Submission> getSubmissionsByChallengeIdAndUserDataId(Long challengeId, UserData userData);

    /**
     * Checks if a challenge is passed in the userData
     * Returns false if no challenge instance found
     * @param challengeId id
     * @param userData The userData id
     * @return If a challenge is passed in the userData instance
     */
    public abstract Boolean checkIsChallengePassed(Long challengeId, UserData userData);

    /**
     * Returns all passed challenges by user Data
     * A challenge is passed if it has one passed submission
     * @param userData The userData instance
     * @return A List of Challenges that are passed
     */
    public abstract List<Challenge> getAllPassedChallenges(UserData userData);

    /**
     * Returns all challenges that have no passed submission in the userData
     * @param userData The user Data instance where the Data is saved
     * @return A List of challenges that have no passed submission
     */
    public abstract List<Challenge> getAllFailedChallenges(UserData userData);
}
