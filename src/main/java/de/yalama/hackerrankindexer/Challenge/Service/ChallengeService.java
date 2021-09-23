package de.yalama.hackerrankindexer.Challenge.Service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import java.util.List;
import java.util.Set;

public abstract class ChallengeService implements BaseService<Challenge> {
    public abstract Set<Submission> getSubmissionsByChallengeId(Long challengeId, long sesionId);
    public abstract Boolean checkIsChallengePassed(Long challengeId, long sessionId);
    public abstract List<Challenge> getAllPassedChallenges(long sessionId);
    public abstract List<Challenge> getAllFailedChallenges(long sessionId);
}
