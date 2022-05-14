package de.yalama.hackerrankindexer.GeneralPercentage.Service;

import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.GeneralPercentage.Repository.GeneralPercentageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
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
    public void calculatePercentageForUserData(UserData userData) {
        /*if(user.getGeneralPercentage() != null
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
        this.userService.update(user.getId(), user);*/
        //TODO again
    }

    private Double calculateChallengesSolvedPercentage(User user) {
        //TODO
        return null;
    }

    private Double calculateSubmissionsSolvedPercentage(UserData userData) {
        double submissionsPassed = 0; //TODO
        double allSubmissions = userData.getSubmissionList().size() + submissionsPassed;

        return submissionsPassed / allSubmissions;
    }
}
