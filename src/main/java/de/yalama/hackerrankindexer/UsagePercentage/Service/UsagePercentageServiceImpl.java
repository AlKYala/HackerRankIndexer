package de.yalama.hackerrankindexer.UsagePercentage.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.UsagePercentage.Repository.UsagePercentageRepository;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.UserData.Service.UserDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UsagePercentageServiceImpl extends UsagePercentageService{

    private UserDataService userDataService;
    private UsagePercentageRepository usagePercentageRepository;

    public UsagePercentageServiceImpl(UsagePercentageRepository usagePercentageRepository,
                                      UserDataService userDataService) {
        this.userDataService = userDataService;
        this.usagePercentageRepository = usagePercentageRepository;
    }

    public int createAll(UserData userdata) {
        userdata.getUsedPLanguages().stream().forEach(pLanguage -> this.create(userdata, pLanguage));
        this.userDataService.update(userdata.getId(), userdata);
        return 1;
    }

    @Override
    public List<UsagePercentage> findAll() {
        return this.usagePercentageRepository.findAll();
    }

    @Override
    public UsagePercentage create(UserData userData, PLanguage pLanguage) {
        UsagePercentage usagePercentage = new UsagePercentage();

        long numAllSubmissions = this.usagePercentageRepository.findNumberSubmissionsOfUserData(userData.getId());
        long numSubmissionsINLanguage = this.usagePercentageRepository.findNumberSubmissionsInLanguageOfUserData(userData.getId(), pLanguage.getId());

        Integer percentageInt = this.getPercentage(numSubmissionsINLanguage, numAllSubmissions);

        usagePercentage.setUserData(userData);
        usagePercentage.setPLanguage(pLanguage);
        usagePercentage.setTotal(numSubmissionsINLanguage);
        usagePercentage.setPercentage(percentageInt);

        return this.usagePercentageRepository.save(usagePercentage);
    }

    private Integer getPercentage(Long a, Long b) {
        double percentage = ((double) a) / ((double) b);
        percentage *= 100;
        Integer percentageInt = (int) percentage;
        return percentageInt;
    }
}
