package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.GeneralPercentage.Service.GeneralPercentageService;
import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.PassPercentage.Service.PassPercentageService;
import de.yalama.hackerrankindexer.Submission.Repository.SubmissionRepository;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Repository.UserRepository;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.shared.models.UsageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class AnalyticsServiceImpl extends AnalyticsService {

    private UserService             userService;
    private UserRepository          userRepository;
    private SubmissionRepository    submissionRepository;
    private PassPercentageService   passPercentageService;
    private GeneralPercentageService generalPercentageService;

    public AnalyticsServiceImpl(UserService userService,
                                UserRepository userRepository,
                                PassPercentageService passPercentageService,
                                SubmissionRepository submissionRepository,
                                GeneralPercentageService generalPercentageService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passPercentageService = passPercentageService;
        this.submissionRepository = submissionRepository;
        this.generalPercentageService = generalPercentageService;
    }

    @Override
    public void clear(User user) {
        this.userService.deleteById(user.getId());
    }

    @Override
    public boolean checkSubmissionsExist(User user) {
        return !user.getSubmittedEntries().isEmpty();
    }

    @Override
    public GeneralPercentage getGeneralPercentages(User user) {
        if(user.getGeneralPercentage() == null || !user.getGeneralPercentage().isCalculated()) {
            this.generalPercentageService.calculateUsersGeneralPercentages(user);
        }

        return user.getGeneralPercentage();
    }

    @Override
    public Set<UsageData> getUsagePercentages(User user) {
        return this.getUsageDataOfUser(user);
    }

    private Set<UsageData> getUsageDataOfUser(User user) {
        Set<UsagePercentage> usagePercentages   = user.getUsagePercentages();
        Set<UsageData> usageData                = new HashSet<UsageData>();

        Map<Long, PassPercentage> passPercentageMap = this.getPassPercentagesMapOfUser(user);

        for(UsagePercentage usagePercentage : usagePercentages) {

            Long passed = passPercentageMap.get(usagePercentage.getPLanguage().getId()).getPassed(); //always returns 0 because passPercentage is never initiated

            UsageData converted = new UsageData(usagePercentage.getPLanguage(), usagePercentage.getTotal(), passed);
            usageData.add(converted);
        }

        return usageData;
    }

    private Map<Long, PassPercentage> getPassPercentagesMapOfUser(User user) {

        this.passPercentageService.createAll(user);
        Map<Long, PassPercentage> passPercentages = new HashMap<Long, PassPercentage>();

        for(PassPercentage percentage: user.getPassPercentages()) {
            passPercentages.put(percentage.getPLanguage().getId(), percentage);
        }

        //System.out.println(passPercentages);

        return passPercentages;
    }

    @Override
    public Set<PassPercentage> getPassPercentages(User user) {
        return user.getPassPercentages();
    }


    @Override
    public Long getNumberOfUsers() {
        return this.userRepository.count();
    }

    @Override
    public Long getNumberOfSubmissions() {
        return this.submissionRepository.count();
    }
}
