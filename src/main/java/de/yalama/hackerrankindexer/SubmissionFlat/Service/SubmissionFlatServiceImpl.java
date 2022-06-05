package de.yalama.hackerrankindexer.SubmissionFlat.Service;

import de.yalama.hackerrankindexer.SubmissionFlat.Model.SubmissionFlat;
import de.yalama.hackerrankindexer.SubmissionFlat.Repository.SubmissionFlatRepository;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.validator.Validator;
import de.yalama.hackerrankindexer.shared.services.validator.ValidatorOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class SubmissionFlatServiceImpl extends SubmissionFlatService{

    private SubmissionFlatRepository submissionFlatRepository;
    private Validator<SubmissionFlat, SubmissionFlatRepository> validator;

    public SubmissionFlatServiceImpl(SubmissionFlatRepository submissionFlatRepository) {
        this.submissionFlatRepository = submissionFlatRepository;
        this.validator = new Validator<SubmissionFlat, SubmissionFlatRepository>("SubmissionFlat", submissionFlatRepository);
    }

    @Override
    public SubmissionFlat findById(Long id) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, ValidatorOperations.FIND_BY_ID);
        return this.submissionFlatRepository.findById(id).get();
    }

    @Override
    public List<SubmissionFlat> findAll() throws HackerrankIndexerException {
        return this.submissionFlatRepository.findAll();
    }

    @Override
    public SubmissionFlat save(SubmissionFlat instance) throws HackerrankIndexerException {
        this.validator.throwIfExistsByID(instance.getId());
        return this.submissionFlatRepository.save(instance);
    }

    @Override
    public SubmissionFlat update(Long id, SubmissionFlat instance) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, ValidatorOperations.SAVE);
        return this.submissionFlatRepository.save(instance);
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, ValidatorOperations.DELETE);
        this.submissionFlatRepository.deleteById(id);
        return id;
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
