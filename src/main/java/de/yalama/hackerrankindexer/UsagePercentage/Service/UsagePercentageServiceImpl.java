package de.yalama.hackerrankindexer.UsagePercentage.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.UsagePercentage.Repository.UsagePercentageRepository;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UsagePercentageServiceImpl extends UsagePercentageService{

    private UserService userService;
    private UsagePercentageRepository usagePercentageRepository;

    public UsagePercentageServiceImpl(UsagePercentageRepository usagePercentageRepository,
                                      UserService userService) {
        this.userService = userService;
        this.usagePercentageRepository = usagePercentageRepository;
    }

    public int createAll(UserData userdata) {
        /*userdata.getUsedPLanguages().stream().forEach(pLanguage -> this.create(user, pLanguage));
        this.userService.update(user.getId(), user);
        return 1;*/
        //TODO
        return 1;
    }

    @Override
    public List<UsagePercentage> findAll() {
        return this.usagePercentageRepository.findAll();
    }

    @Override
    public UsagePercentage create(UserData userData, PLanguage pLanguage) {
        /*UsagePercentage usagePercentage = new UsagePercentage();
        long numAllSubmissions = user.getSubmittedEntries().size();
        long numSubmissionsINLanguage = this.userService.findSubmissionsOfUserOfLanguage(user, pLanguage).size();
        double percentage = ((double) numSubmissionsINLanguage) / ((double) numAllSubmissions);
        usagePercentage.setUser(user);
        usagePercentage.setPLanguage(pLanguage);
        usagePercentage.setTotal(numSubmissionsINLanguage);
        usagePercentage.setPercentage(percentage);
        UsagePercentage result = this.usagePercentageRepository.save(usagePercentage);
        user.getUsagePercentages().add(usagePercentage);
        return result;*/
        //TODO
        return null;
    }
}
