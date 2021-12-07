package de.yalama.hackerrankindexer.Challenge.Service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import java.util.List;
import java.util.Set;

public abstract class ChallengeService implements BaseService<Challenge> {
    public abstract Set<Submission> getSubmissionsByChallengeId(Long challengeId, User user);
    public abstract Boolean checkIsChallengePassed(Long challengeId, User user);
    public abstract List<Challenge> getAllPassedChallenges(User user);
    public abstract List<Challenge> getAllFailedChallenges(User user);
}
