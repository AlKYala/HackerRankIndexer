package de.yalama.hackerrankindexer.Challenge.Service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import java.util.List;

public abstract class ChallengeService implements BaseService<Challenge> {
    public abstract List<Submission> getSubmissionsByChallengeId(Long challengeId);
}
