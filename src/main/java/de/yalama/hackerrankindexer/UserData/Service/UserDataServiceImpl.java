package de.yalama.hackerrankindexer.UserData.Service;

import de.yalama.hackerrankindexer.GeneralPercentage.Repository.GeneralPercentageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.PassPercentage.Repository.PassPercentageRepository;
import de.yalama.hackerrankindexer.SubmissionFlat.Model.SubmissionFlat;
import de.yalama.hackerrankindexer.SubmissionFlat.Repository.SubmissionFlatRepository;
import de.yalama.hackerrankindexer.Submission.Repository.SubmissionRepository;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.UsagePercentage.Repository.UsagePercentageRepository;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.UserData.Model.UserDataFlat;
import de.yalama.hackerrankindexer.UserData.Repository.UserDataRepository;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.validator.Validator;
import de.yalama.hackerrankindexer.shared.services.validator.ValidatorOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserDataServiceImpl extends UserDataService {

    private UserDataRepository      userDataRepository;
    private SubmissionFlatRepository submissionFlatRepository;
    private SubmissionRepository submissionRepository;
    private UsagePercentageRepository usagePercentageRepository;
    private Validator<UserData, UserDataRepository> validator;
    private PassPercentageRepository passPercentageRepository;
    private GeneralPercentageRepository generalPercentageRepository;

    public UserDataServiceImpl(UserDataRepository userDataRepository,
                               SubmissionFlatRepository submissionFlatRepository,
                               SubmissionRepository submissionRepository,
                               UsagePercentageRepository usagePercentageRepository,
                               GeneralPercentageRepository generalPercentageRepository,
                               PassPercentageRepository passPercentageRepository) {
        this.userDataRepository     = userDataRepository;
        this.usagePercentageRepository = usagePercentageRepository;
        this.submissionFlatRepository = submissionFlatRepository;
        this.validator =
                new Validator<UserData, UserDataRepository>("UserData", userDataRepository);
        this.passPercentageRepository = passPercentageRepository;
        this.submissionRepository = submissionRepository;
        this.generalPercentageRepository = generalPercentageRepository;
    }

    @Override
    public UserData findById(Long id) throws HackerrankIndexerException {
        return this.userDataRepository.findById(id).get();
    }

    @Override
    public List<UserData> findAll() throws HackerrankIndexerException {
        return this.userDataRepository.findAll();
    }

    @Override
    public UserData save(UserData instance) throws HackerrankIndexerException {
        return this.userDataRepository.save(instance);
    }

    @Override
    public UserData update(Long id, UserData instance) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, ValidatorOperations.SAVE);
        return this.userDataRepository.save(instance);
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        this.userDataRepository.deleteById(id);
        return id;
    }

    @Override
    public List<UserData> findByUser(User user) {
        return this.userDataRepository.getByUser(user.getId());
    }

    @Override
    public Collection<SubmissionFlat> findSubmissionsOfUserOfPlanguage(UserData userData, PLanguage pLanguage) {
        return this.submissionFlatRepository.getSubmissionsByPlanguageIdAndUserDataId(pLanguage.getId(), userData.getId());
    }

    @Override
    public UserData findUserDataByToken(String token) {
        return this.userDataRepository.getByUserDataToken(token);
    }

    @Override
    public List<UserDataFlat> getUserDataFlat(User user) {
        List<UserDataFlat> userDataFlat = new ArrayList<UserDataFlat>();
        this.findByUser(user).forEach((userData -> {
            UserDataFlat temp = new UserDataFlat(userData.getDateCreated(), userData.getToken());
            userDataFlat.add(temp);
        }));
        return userDataFlat;
    }

    @Override
    public UserData generateQRCode(Long userDataId) {
        UserData userData = this.findById(userDataId);
        if(userData == null)
            return null;
        String token = String.format("%d%d", userData.hashCode(), userData.getDateCreated().hashCode());
        userData.setToken(token);
        return this.update(userDataId, userData);
    }

    @Override
    public List<UserDataFlat> removeEntryFromUserData(Integer index, User user) {
        List<UserData> elements = this.findByUser(user);

        if(index >= elements.size())
            return null;

        UserData toDelete = elements.get(index);
        this.detachChildren(toDelete);
        this.deleteById(toDelete.getId());
        return this.getUserDataFlat(user);
    }

    private void detachChildren(UserData userData) {
        this.detachUsagePercentages(userData);
        this.detachPassPercentages(userData);
        this.detachSubmissions(userData);
        this.detachGeneralPercentage(userData);
    }

    private void detachUsagePercentages(UserData userData) {
        Set<UsagePercentage> usagePercentages = userData.getUsagePercentages();
        for(UsagePercentage usagePercentage : usagePercentages) {
            this.usagePercentageRepository.deleteById(usagePercentage.getId());
        }
    }

    private void detachPassPercentages(UserData userData) {
        Set<PassPercentage> passPercentages = userData.getPassPercentages();
        for(PassPercentage passPercentage: passPercentages) {
            this.passPercentageRepository.deleteById(passPercentage.getId());
        }
    }

    private void detachGeneralPercentage(UserData userData) {
        this.generalPercentageRepository.deleteById(userData.getGeneralPercentage().getId());
    }

    private void detachSubmissions(UserData userData) {
        List<SubmissionFlat> submissions = userData.getSubmissionList();
        for(SubmissionFlat submissionFlat : submissions) {
            this.submissionFlatRepository.deleteById(submissionFlat.getId());
            this.submissionRepository.deleteById(submissionFlat.getId());
        }
    }
}
