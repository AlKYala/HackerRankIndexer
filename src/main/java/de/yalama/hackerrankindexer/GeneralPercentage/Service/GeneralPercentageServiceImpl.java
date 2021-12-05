package de.yalama.hackerrankindexer.GeneralPercentage.Service;

import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.GeneralPercentage.Repository.GeneralPercentageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class GeneralPercentageServiceImpl extends GeneralPercentageService{

    private UserService userService;
    private GeneralPercentageRepository generalPercentageRepository;

    public GeneralPercentageServiceImpl(UserService userService, GeneralPercentageRepository generalPercentageRepository) {
        this.userService = userService;
        this.generalPercentageRepository = generalPercentageRepository;
    }

    @Override
    public GeneralPercentage findByUser(User user) {
        return user.getGeneralPercentage();
    }

    @Override
    public GeneralPercentage create(User user) {
        GeneralPercentage generalPercentage = new GeneralPercentage();
        generalPercentage.setUser(user);
        generalPercentage.setFavouriteLanguage(this.userService.getFavouriteLanguage(user));
        generalPercentage.setPercentageSubmissionsPassed(this.calculateSubmissionPercentage(user));
        generalPercentage.setPercentageChallengesSolved(this.calculateChallengePercentage(user));
        return this.generalPercentageRepository.save(generalPercentage);
    }

    @Override
    public Double calculateSubmissionPercentage(User user) {
        double numberSubmissions = (double) user.getSubmittedEntries().size();
        double numberPassedSubmissions = (double) user.getSubmittedEntries().stream().filter(submission -> submission.getScore() >= 1)
                .count();
        return numberPassedSubmissions/numberSubmissions;
    }

    @Override
    public Double calculateChallengePercentage(User user) {
        Set<Long> allChallengeIDs = new HashSet<Long>();
        Set<Long> passedChallengeIDs = new HashSet<Long>();
        for(Submission submission : user.getSubmittedEntries()) {
            allChallengeIDs.add(submission.getId());
            if(submission.getScore() >= 1) {
                passedChallengeIDs.add(submission.getId());
            }
        }
        return ((double) passedChallengeIDs.size()) / ((double) passedChallengeIDs.size());
    }
}
