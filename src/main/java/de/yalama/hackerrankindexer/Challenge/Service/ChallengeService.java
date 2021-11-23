package de.yalama.hackerrankindexer.Challenge.Service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class ChallengeService implements BaseService<Challenge> {
    public abstract Set<Submission> getSubmissionsByChallengeIdAndSessionId(long challengeId, String sessionId);
    public abstract boolean checkIsChallengePassedBySessionId(long challengeId, String sessionId);
    public abstract List<Challenge> getAllChallengesBySessionId(String sessionId);
    public abstract List<Challenge> getAllPassedChallengesBySessionId(String sessionId);
    public abstract boolean checkChallengeAlreadyExists(String challengeName);
    public abstract Challenge getChallengeByName(String challengeName);
    public abstract Challenge persist(Challenge challenge);

    /**
     *
     * @param challengeId The id of the challenge
     * @param sessionId The sessionId of the requesting client
     * @return The most recent passed submission of sessionId for specified challenge
     */
    public abstract Submission getMostRecentPassedSubmissionBySessionIdAndChallenge(long challengeId, String sessionId);

    /**
     *
     * @param sessionId The sessionid of the requesting client
     * @return The most recent passed submission of every challenge
     */
    public abstract Collection<Submission> getMostRecentPassedSubmissionBySessionIdForAllChallenges(String sessionId);
}
