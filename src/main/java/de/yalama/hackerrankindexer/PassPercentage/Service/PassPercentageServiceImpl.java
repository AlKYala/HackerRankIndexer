package de.yalama.hackerrankindexer.PassPercentage.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.PassPercentage.Repository.PassPercentageRepository;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class PassPercentageServiceImpl extends PassPercentageService{

    private PassPercentageRepository passPercentageRepository;

    private UserService userService;

    public PassPercentageServiceImpl(PassPercentageRepository passPercentageRepository,
                                     UserService userService) {
        this.passPercentageRepository = passPercentageRepository;
        this.userService = userService;
    }

    @Override
    public PassPercentage findById(long id) {
        return passPercentageRepository.findById(id).get();
    }

    @Override
    public int createAll(User user) {
        user.getUsedPLanguages().stream().forEach(pLanguage -> this.create(user, pLanguage));
        this.userService.update(user.getId(), user);
        return 1;
    }

    @Override
    public PassPercentage create(User user, PLanguage pLanguage) {
        PassPercentage passPercentage = new PassPercentage();
        passPercentage.setUser(user);
        passPercentage.setPLanguage(pLanguage);
        Set<Submission> submissions = this.userService.findSubmissionsOfUserOfLanguage(user, pLanguage);
        long total = submissions.size();
        long passed = submissions.stream().filter(submission -> submission.getScore() >= 1).count();
        double percentage = ((double) passed) / ((double) total);
        passPercentage.setTotal(total);
        passPercentage.setPercentage(percentage);
        PassPercentage result = this.passPercentageRepository.save(passPercentage);
        user.getPassPercentages().add(result);
        return passPercentage;
    }
}
