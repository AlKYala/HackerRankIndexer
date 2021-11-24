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
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
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
    public Set<Submission> getSubmissionsByChallengeIdAndSessionId(long challengeId, String sessionId) {
        return this.findById(challengeId).getSubmissions()
                .stream()
                .filter(submission -> submission.getSessionId().equals(sessionId))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean checkIsChallengePassedBySessionId(long challengeId, String sessionId) {
        for(Submission submission : this.getSubmissionsByChallengeIdAndSessionId(challengeId, sessionId)) {
            if(submission.getScore() == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Challenge> getAllChallengesBySessionId(String sessionId) {
        return this.findAll().stream()
                .filter(challenge -> this.checkHasSubmissionBySessionId(challenge, sessionId))
                .collect(Collectors.toList());
    }

    private boolean checkHasSubmissionBySessionId(Challenge challenge, String sessionId) {
        for(Submission submission : challenge.getSubmissions()) {
            if(submission.getSessionId().equals(sessionId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Challenge> getAllPassedChallengesBySessionId(String sessionId) {
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

    @Override
    public Submission getMostRecentPassedSubmissionBySessionIdAndChallenge(long challengeId, String sessionId) {
        Submission[] result = new Submission[1];
        this.getSubmissionsByChallengeIdAndSessionId(challengeId, sessionId)
                .stream()
                .forEach(submission -> result[0] = this.getMoreRecentPassedSubmission(result[0], submission));
        return result[0];
    }

    /**
     * a is the default
     * if b has higher ID and is passed, return b
     * @param a, b
     * @return see logic
     */
    private Submission getMoreRecentPassedSubmission(Submission a, Submission b) {
        if(a == null) {
            return (b.getScore() == 1) ? b : null;
        }
        return (b.getScore() < 1 || a.getId() > b.getId()) ? a : b;
    }

    @Override
    public List<Submission> getMostRecentPassedSubmissionBySessionIdForAllChallenges(String sessionId) {
        List<Submission> passedSubmissions = new ArrayList<Submission>();

        this.findAll()
                .stream()
                .forEach(challenge -> this.addIfValid(challenge, sessionId, passedSubmissions));

        return passedSubmissions;
    }

    /**
     * Only adds a found submission the the list if
     * @param c The challenge to find passed submission for
     * @param sessionId The sessionid
     * @param submissions The list of submissions
     */
    private void addIfValid(Challenge c, String sessionId, List<Submission> submissions) {
        Submission found = this.getMostRecentPassedSubmissionBySessionIdAndChallenge(c.getId(), sessionId);
        if(found == null) {
            return;
        }
        submissions.add(found);
    }

    @Override
    public Collection<Submission> getMostRecentPassedSubmissionBySessionIdForAllChallengesOfLangauge(long languageId, String sessionId) {
        return this.getMostRecentPassedSubmissionBySessionIdForAllChallenges(sessionId)
                .stream()
                .filter(submission -> submission.getLanguage().getId() == languageId)
                .collect(Collectors.toList());
    }
}
