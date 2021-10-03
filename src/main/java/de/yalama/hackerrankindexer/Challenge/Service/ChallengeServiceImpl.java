package de.yalama.hackerrankindexer.Challenge.Service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Challenge.Repository.ChallengeRepository;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.ServiceHandler;
import de.yalama.hackerrankindexer.shared.services.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<Submission> getSubmissionsByChallengeIdAndSessionId(long challengeId, long sessionId) {
        return this.findById(challengeId).getSubmissions()
                .stream()
                .filter(submission -> submission.getSessionId() == sessionId)
                .collect(Collectors.toSet());
    }

    @Override
    public Boolean checkIsChallengePassedBySessionId(long challengeId, long sessionId) {
        for(Submission submission : this.getSubmissionsByChallengeIdAndSessionId(challengeId, sessionId)) {
            if(submission.getScore() == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Challenge> getAllChallengesBySessionId(long sessionId) {
        return this.findAll().stream()
                .filter(challenge -> this.checkHasSubmissionBySessionId(challenge, sessionId))
                .collect(Collectors.toList());
    }

    private boolean checkHasSubmissionBySessionId(Challenge challenge, long sessionId) {
        for(Submission submission : challenge.getSubmissions()) {
            if(submission.getSessionId() == sessionId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Challenge> getAllPassedChallengesBySessionId(long sessionId) {
        return this.findAll()
                .stream()
                .filter(challenge -> this.checkIsChallengePassedBySessionId(challenge.getId(), sessionId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkChallengeAlreadyExists(String challengeName) {
        return this.getChallengeByName(challengeName) != null;
    }

    @Override
    public Challenge getChallengeByName(String challengeName) {
        for(Challenge challenge : this.findAll()) {
            if(challenge.getChallengeName().equals(challengeName)) {
                return challenge;
            }
        }
        return null;
    }

    @Override
    public Challenge persist(Challenge challenge) {
        Challenge foundChallenge = this.getChallengeByName(challenge.getChallengeName());
        if(foundChallenge != null) {
            return foundChallenge;
        }
        return this.save(challenge);
    }
}
