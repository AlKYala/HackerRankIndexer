package de.yalama.hackerrankindexer.GeneralPercentage.Service;

import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.GeneralPercentage.Repository.GeneralPercentageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
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
    private ChallengeService challengeService;
    private SubmissionService submissionService;

    public GeneralPercentageServiceImpl(UserService userService,
                                        GeneralPercentageRepository generalPercentageRepository,
                                        SubmissionService submissionService, ChallengeService challengeService) {
        this.userService = userService;
        this.generalPercentageRepository = generalPercentageRepository;
        this.submissionService = submissionService;
        this.challengeService = challengeService;
    }

    @Override
    public void calculateUsersGeneralPercentages(User user) {
        if(user.getGeneralPercentage() != null
                && user.getGeneralPercentage().isCalculated()
                && user.getGeneralPercentage().getFavouriteLanguage() != null) {
            return;
        }
        double challengesSolvedPercentage = this.calculateChallengesSolvedPercentage(user) * 100;
        double submissionsPassedPercentage = this.calculateSubmissionsSolvedPercentage(user) * 100;

        PLanguage favoritePLanguage = this.userService.getFavouriteLanguage(user);

        GeneralPercentage generalPercentage = new GeneralPercentage();

        generalPercentage.setPercentageChallengesSolved(challengesSolvedPercentage);
        generalPercentage.setPercentageSubmissionsPassed(submissionsPassedPercentage);
        generalPercentage.setCalculated(true);
        generalPercentage.setFavouriteLanguage(favoritePLanguage);

        generalPercentage.setUser(user);

        user.setGeneralPercentage(generalPercentage);

        this.generalPercentageRepository.save(generalPercentage);
        this.userService.update(user.getId(), user);
    }

    private Double calculateChallengesSolvedPercentage(User user) {
        double challengesPassed = this.challengeService.getAllPassedChallenges(user).size();
        double challengesAttempted = this.challengeService.getAllFailedChallenges(user).size() + challengesPassed;

        return challengesPassed / challengesAttempted;
    }

    private Double calculateSubmissionsSolvedPercentage(User user) {
        double submissionsPassed = this.submissionService.getAllPassed(user).size();
        double allSubmissions = user.getSubmittedEntries().size() + submissionsPassed;

        return submissionsPassed / allSubmissions;
    }
}
