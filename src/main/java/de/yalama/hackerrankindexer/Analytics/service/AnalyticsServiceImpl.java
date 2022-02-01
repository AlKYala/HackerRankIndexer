package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.Submission.Repository.SubmissionRepository;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Repository.UserRepository;
import de.yalama.hackerrankindexer.User.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class AnalyticsServiceImpl extends AnalyticsService {

    private UserService             userService;
    private UserRepository          userRepository;
    private SubmissionRepository    submissionRepository;

    public AnalyticsServiceImpl(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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
        return user.getGeneralPercentage();
    }

    @Override
    public Set<UsagePercentage> getUsagePercentages(User user) {
        return user.getUsagePercentages();
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
