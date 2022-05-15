package de.yalama.hackerrankindexer.PassPercentage.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.PassPercentage.Repository.PassPercentageRepository;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.UserData.Service.UserDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class PassPercentageServiceImpl extends PassPercentageService {

    private PassPercentageRepository passPercentageRepository;
    public UserDataService userDataService;

    public PassPercentageServiceImpl(PassPercentageRepository passPercentageRepository,
                                     UserDataService userDataService) {
        this.passPercentageRepository = passPercentageRepository;
        this.userDataService = userDataService;
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
}
