package de.yalama.hackerrankindexer.Challenge.Service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import java.util.Set;

public abstract class ChallengeService implements BaseService<Challenge> {
    public abstract Set<Submission> getSubmissionsByChallengeId(Long challengeId);
}
