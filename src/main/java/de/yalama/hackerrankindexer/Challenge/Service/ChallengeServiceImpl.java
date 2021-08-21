package de.yalama.hackerrankindexer.Challenge.Service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Challenge.Repository.ChallengeRepository;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.ServiceHandler;
import de.yalama.hackerrankindexer.shared.services.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChallengeServiceImpl extends ChallengeService {

    private ChallengeRepository challengeRepository;
    private Validator<Challenge, ChallengeRepository> validator;
    private ServiceHandler<Challenge, ChallengeRepository> serviceHandler;

    public ChallengeServiceImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
        this.validator = new Validator<Challenge, ChallengeRepository>("Challenge", this.challengeRepository);
        this.serviceHandler =
                new ServiceHandler<Challenge, ChallengeRepository>(this.challengeRepository, this.validator);
    }

    @Override
    public Challenge findById(Long id) throws HackerrankIndexerException {
        return this.serviceHandler.findById(id);
    }

    @Override
    public List<Challenge> findAll() throws HackerrankIndexerException {
        return this.serviceHandler.findAll();
    }

    @Override
    public Challenge save(Challenge instance) throws HackerrankIndexerException {
        return this.serviceHandler.save(instance);
    }

    @Override
    public Challenge update(Long id, Challenge instance) throws HackerrankIndexerException {
        return this.serviceHandler.update(id, instance);
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, 1);
        this.findById(id).getSubmissions().forEach(submission -> submission.setChallenge(null));
        return this.serviceHandler.deleteById(id);
    }

    @Override
    public List<Submission> getSubmissionsByChallengeId(Long challengeId) {
        Challenge challenge = this.findById(challengeId);
        return new ArrayList<Submission>(challenge.getSubmissions());
    }
}
