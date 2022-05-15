package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.GeneralPercentage.Service.GeneralPercentageService;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.PassPercentage.Service.PassPercentageService;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.Submission.Repository.SubmissionRepository;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Repository.UserRepository;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.shared.models.UsageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    private Set<UsageData> getUsageDataOfUser(User user) {
        //TODO
        return null;
    }

    private Map<Long, PassPercentage> getPassPercentagesMapOfUser(User user) {
        //TODO
        return null;
    }


    @Override
    public boolean checkSubmissionsExist(User user) {
        return user.getUserData() != null && user.getUserData().size() > 0;
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
