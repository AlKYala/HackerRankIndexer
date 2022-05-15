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
    public PassPercentage findById(long id) {
        return passPercentageRepository.findById(id).get();
    }

    @Override
    public int createAll(UserData userData) {

        //TODO
        /*user.getUsedPLanguages().stream().forEach(pLanguage -> this.create(user, pLanguage));
        this.userService.update(user.getId(), user);*/
        return 1;
    }

    @Override
    public PassPercentage create(UserData userData, PLanguage pLanguage) {
        //TODO
        /*PassPercentage passPercentage = this.findByUserAndLanguage(user, pLanguage);
        if(passPercentage != null) {
            return passPercentage;
        }
        passPercentage = new PassPercentage();
        passPercentage.setUser(user);
        passPercentage.setPLanguage(pLanguage);
        Set<Submission> submissions = this.userService.findSubmissionsOfUserOfLanguage(user, pLanguage);
        long total = submissions.size();
        long passed = submissions.stream().filter(submission -> submission.getScore() >= 1).count();
        passPercentage.setTotal(total);
        passPercentage.setPassed(passed);
        PassPercentage result = this.passPercentageRepository.save(passPercentage);
        user.getPassPercentages().add(result);
        return passPercentage;*/
        return null;
    }

    @Override
    public PassPercentage findByUserAndLanguage(UserData userData, PLanguage pLanguage) {
        return null;
    }

    @Override
    public boolean existsByUserAndPLanguage(UserData userData, PLanguage pLanguage) {
        return false;
    }

    /*@Override
    public PassPercentage findByUserAndLanguage(User user, PLanguage pLanguage) {
        PassPercentage found = null;
        try {
            found = this.passPercentageRepository.findAll()
                    .stream()
                    .filter(passPercentage -> this.checkPassPercentageIsOfUserAndLanguage(user, pLanguage, passPercentage))
                    .findFirst().get();
        } catch (Exception e) {
            //do nothing
        }

        return found;
    }

    @Override
    public boolean existsByUserAndPLanguage(User user, PLanguage pLanguage) {
        return this.findByUserAndLanguage(user, pLanguage) != null;
    }*/

    private boolean checkPassPercentageIsOfUserAndLanguage(User user, PLanguage pLanguage, PassPercentage percentage) {
        /*return percentage.getUser().getId() == user.getId()
                && percentage.getPLanguage().getId() == pLanguage.getId();*/
        return false;
    }
}
