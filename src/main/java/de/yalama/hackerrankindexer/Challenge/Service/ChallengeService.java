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
     * Returns all passed challenges by user Data
     * A challenge is passed if it has one passed submission
     * @param userData The userData instance
     * @return A List of Challenges that are passed
     */
    public abstract List<Challenge> getAllPassedChallenges(Long userDataId);

    /**
     * Returns all challenges that have no passed submission in the userData
     * @param userData The user Data instance where the Data is saved
     * @return A List of challenges that have no passed submission
     */
    public abstract List<Challenge> getAllFailedChallenges(Long userDataId);
}
