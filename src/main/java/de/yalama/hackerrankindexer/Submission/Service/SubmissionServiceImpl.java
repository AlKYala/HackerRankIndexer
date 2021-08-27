package de.yalama.hackerrankindexer.Submission.Service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Challenge.Repository.ChallengeRepository;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.Contest.Model.Contest;
import de.yalama.hackerrankindexer.Contest.Repository.ContestRepository;
import de.yalama.hackerrankindexer.PLanguage.Repository.PLanguageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Repository.SubmissionRepository;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Repository.UserRepository;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.ServiceHandler;
import de.yalama.hackerrankindexer.shared.services.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubmissionServiceImpl extends SubmissionService {

    private SubmissionRepository submissionRepository;
    private Validator<Submission, SubmissionRepository> validator;
    private ServiceHandler<Submission, SubmissionRepository> serviceHandler;
    private ContestRepository contestRepository;
    private ChallengeRepository challengeRepository;
    private ChallengeService challengeService;
    private PLanguageRepository pLanguageRepository;
    private UserRepository userRepository;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, ContestRepository contestRepository,
                                 ChallengeRepository challengeRepository, PLanguageRepository pLanguageRepository,
                                 UserRepository userRepository, ChallengeService challengeService) {
        this.submissionRepository = submissionRepository;
        this.validator =
                new Validator<Submission, SubmissionRepository>("Submission", this.submissionRepository);
        this.serviceHandler =
                new ServiceHandler<Submission, SubmissionRepository>(this.submissionRepository, this.validator);
        this.userRepository = userRepository;
        this.pLanguageRepository = pLanguageRepository;
        this.challengeRepository = challengeRepository;
        this.contestRepository = contestRepository;
        this.challengeService = challengeService;
    }

    @Override
    public Submission findById(Long id) throws HackerrankIndexerException {
        return this.serviceHandler.findById(id);
    }

    @Override
    public List<Submission> findAll() throws HackerrankIndexerException {
        return this.serviceHandler.findAll();
    }

    @Override
    public Submission save(Submission instance) throws HackerrankIndexerException {
        return this.serviceHandler.save(instance);
    }

    @Override
    public Submission update(Long id, Submission instance) throws HackerrankIndexerException {
        return this.serviceHandler.update(id, instance);
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, 1);
        Submission toDelete = this.submissionRepository.getById(id);
        this.removeSubmissionFromChallenge(toDelete);
        this.removeSubmissionFromUser(toDelete);
        this.removeSubmissionFromPLanguage(toDelete);
        this.removeSubmissionFromContest(toDelete);
        return this.serviceHandler.deleteById(id);
    }

    private void removeSubmissionFromPLanguage(Submission toDelete) {
        PLanguage pLanguage = this.pLanguageRepository.getById(toDelete.getLanguage().getId());
        pLanguage.getSubmissions()
                .removeIf(submission -> submission.getId() == toDelete.getId());
    }

    private void removeSubmissionFromContest(Submission toDelete) {
        Contest contest = this.contestRepository.getById(toDelete.getContest().getId());
        contest.getSubmissions().removeIf(submission -> submission.getId() == toDelete.getId());
    }

    private void removeSubmissionFromChallenge(Submission toDelete) {
        Challenge challenge = this.challengeRepository.getById(toDelete.getChallenge().getId());
        challenge.getSubmissions()
                .removeIf(submission -> submission.getId() == toDelete.getId());
    }

    private void removeSubmissionFromUser(Submission toDelete) {
        User user = this.userRepository.getById(toDelete.getId());
        user.getSubmittedEntries().removeIf(submission -> submission.getId() == toDelete.getId());
    }
}
