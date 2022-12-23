package de.yalama.hackerrankindexer.PassPercentage.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.PassPercentage.Repository.PassPercentageRepository;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.UserData.Service.UserDataService;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.validator.Validator;
import de.yalama.hackerrankindexer.shared.services.validator.ValidatorOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class PassPercentageServiceImpl extends PassPercentageService {

    private PassPercentageRepository passPercentageRepository;
    public UserDataService userDataService;
    private Validator<PassPercentage, PassPercentageRepository> validator;

    public PassPercentageServiceImpl(PassPercentageRepository passPercentageRepository,
                                     UserDataService userDataService) {
        this.passPercentageRepository = passPercentageRepository;
        this.userDataService = userDataService;
        this.validator = new Validator<>("PassPercentage", passPercentageRepository);
    }

    @Override
    public int createAll(UserData userData) {
        userData.getUsedPLanguages().stream().forEach(pLanguage -> this.create(userData, pLanguage));
        this.userDataService.update(userData.getId(), userData);
        return 1;
    }

    @Override
    public PassPercentage create(UserData userData, PLanguage pLanguage) {
        PassPercentage percentage = this.findByUserDataAndPLanguage(userData, pLanguage);

        if(percentage != null)
            return percentage;

        percentage = new PassPercentage();
        percentage.setUserData(userData);
        percentage.setPLanguage(pLanguage);

        long total = findNumberOfSubmissionsOfUserDataAndLanguage(userData, pLanguage);
        long passed = findNumberOfPassedSubmissionsOfUserDataAndLanguage(userData, pLanguage);
        percentage.setTotal(total);
        percentage.setPassed(passed);

        percentage = this.passPercentageRepository.save(percentage);
        userData.getPassPercentages().add(percentage);
        return percentage;
    }

    private PassPercentage findByUserDataAndPLanguage(UserData ud, PLanguage pLanguage) {
        return this.passPercentageRepository.findByUserAndLanguage(ud.getId(), pLanguage.getId());
    }

    private long findNumberOfPassedSubmissionsOfUserDataAndLanguage(UserData ud, PLanguage pLanguage) {
        return this.passPercentageRepository
                .findNumberOfPassedSubmissionsOfUserDataAndLanguage(ud.getId(), pLanguage.getId());
    }

    private long findNumberOfSubmissionsOfUserDataAndLanguage(UserData ud, PLanguage pLanguage) {
        return this.passPercentageRepository
                .findNumberOfSubmissionsOfUserDataAndLanguage(ud.getId(), pLanguage.getId());
    }

    @Override
    public PassPercentage findById(Long id) throws HackerrankIndexerException {
        return this.passPercentageRepository.findById(id).get();
    }

    @Override
    public List<PassPercentage> findAll() throws HackerrankIndexerException {
        return this.passPercentageRepository.findAll();
    }

    @Override
    public PassPercentage save(PassPercentage instance) throws HackerrankIndexerException {
        return this.passPercentageRepository.save(instance);
    }

    @Override
    public PassPercentage update(Long id, PassPercentage instance) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, ValidatorOperations.SAVE);
        return this.passPercentageRepository.save(instance);
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        this.passPercentageRepository.deleteById(id);
        return id;
    }
}
