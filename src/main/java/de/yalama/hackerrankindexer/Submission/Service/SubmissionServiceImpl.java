package de.yalama.hackerrankindexer.Submission.Service;

import de.yalama.hackerrankindexer.Challenge.Repository.ChallengeRepository;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.Contest.Repository.ContestRepository;
import de.yalama.hackerrankindexer.PLanguage.Repository.PLanguageRepository;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Model.SubmissionFlat;
import de.yalama.hackerrankindexer.Submission.Repository.SubmissionFlatRepository;
import de.yalama.hackerrankindexer.Submission.Repository.SubmissionRepository;
import de.yalama.hackerrankindexer.User.Repository.UserRepository;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.ServiceHandler;
import de.yalama.hackerrankindexer.shared.services.validator.Validator;
import de.yalama.hackerrankindexer.shared.services.validator.ValidatorOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class SubmissionServiceImpl extends SubmissionService {

    private SubmissionRepository submissionRepository;
    private SubmissionFlatRepository submissionFlatRepository;
    private Validator<Submission, SubmissionRepository> validator;
    private ServiceHandler<Submission, SubmissionRepository> serviceHandler;
    private ContestRepository contestRepository;
    private ChallengeRepository challengeRepository;
    private ChallengeService challengeService;
    private PLanguageRepository pLanguageRepository;
    private UserRepository userRepository;
    private Comparator<Submission> submissionIdComparator;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, ContestRepository contestRepository,
                                 ChallengeRepository challengeRepository, PLanguageRepository pLanguageRepository,
                                 UserRepository userRepository, ChallengeService challengeService,
                                 SubmissionFlatRepository submissionFlatRepository) {
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
        this.submissionIdComparator = Comparator.comparing(Submission::getId);
        this.submissionFlatRepository = submissionFlatRepository;
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
        this.validator.throwIfNotExistsByID(id, ValidatorOperations.DELETE);
        Submission toDelete = this.submissionRepository.getById(id);
        this.removeSubmissionFromChallenge(toDelete);
        this.removeSubmissionFromPLanguage(toDelete);
        this.removeSubmissionFromContest(toDelete);
        return this.serviceHandler.deleteById(id);
    }

    private void removeSubmissionFromPLanguage(Submission toDelete) {
        this.deleteById(toDelete.getId());
    }

    private void removeSubmissionFromContest(Submission toDelete) {
        this.deleteById(toDelete.getId());
    }

    private void removeSubmissionFromChallenge(Submission toDelete) {
        this.deleteById(toDelete.getId());
    }

    @Override
    public Collection<SubmissionFlat> getSubmissionsFromIDs(Collection<Long> submissionIDs) {
        return this.submissionFlatRepository.getSubmissionsFromIDs(submissionIDs);
    }

    @Override
    public List<SubmissionFlat> getAllPassed(UserData userData) {
        return this.submissionFlatRepository.getAllPassed(userData.getId());
    }

    @Override
    public List<SubmissionFlat> findAllByUserDataId(Long userDataId) {
        return this.submissionFlatRepository.findAllByUserDataId(userDataId);
    }

    @Override
    public List<SubmissionFlat> getLastPassedFromAll(UserData userData) {
        Map<Long, SubmissionFlat> challengeIdToSubmission = new HashMap<Long, SubmissionFlat>();
        List<SubmissionFlat> allPassed = this.getAllPassed(userData);
        allPassed.forEach((submissionFlat -> challengeIdToSubmission.put(submissionFlat.getChallenge().getId(), submissionFlat)));
        return new ArrayList<SubmissionFlat>(challengeIdToSubmission.values());
    }

    @Override
    public List<SubmissionFlat> getAllFailed(UserData userData) {
        return this.submissionFlatRepository.getAllFailed(userData.getId());
    }

    @Override
    public List<SubmissionFlat> getSubmissionsByChallengeIdAndUserDataId(Long challengeId, Long userDataId) {
        return this.submissionFlatRepository.getSubmissionsByChallengeIdAndUserDataId(challengeId, userDataId);
    }

    @Override
    public Set<SubmissionFlat> getSubmissionsByLanguagesAndUserDataId(List<Long> pLanguageIds, Long userDataId) {
        return null;
    }
}
