package de.yalama.hackerrankindexer.GeneralPercentage.Service;

import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.GeneralPercentage.Repository.GeneralPercentageRepository;
import de.yalama.hackerrankindexer.PLanguage.Repository.PLanguageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.UserData.Repository.UserDataRepository;
import de.yalama.hackerrankindexer.UserData.Service.UserDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class GeneralPercentageServiceImpl extends GeneralPercentageService {


    private GeneralPercentageRepository generalPercentageRepository;
    private UserDataService             userDataService;
    private EntityManager               em;

    public GeneralPercentageServiceImpl(UserDataService userDataService,
                                        GeneralPercentageRepository generalPercentageRepository,
                                        EntityManager entityManager) {
        this.generalPercentageRepository = generalPercentageRepository;
        this.em = entityManager;
        this.userDataService = userDataService;
    }

    @Override
    public void calculatePercentageForUserData(UserData userData) {

        GeneralPercentage found = userData.getGeneralPercentage();

        if(found != null && found.isCalculated() && found.getFavouriteLanguage() != null) {
            return;
        }

        PLanguage favoritePLanguage = this.findFavouriteLanguageForUserData(userData);

        GeneralPercentage generalPercentage = new GeneralPercentage();

        generalPercentage.setUserdata(userData);

        Integer challengesPassedPercentage = this.getChallengesPassedPercentage(userData);
        Integer submissionsPassedPercentage = this.getSubmissionsPassedPercentage(userData);

        generalPercentage.setPercentageChallengesSolved(challengesPassedPercentage);
        generalPercentage.setPercentageSubmissionsPassed(submissionsPassedPercentage);
        generalPercentage.setCalculated(true);
        generalPercentage.setFavouriteLanguage(favoritePLanguage);

        this.generalPercentageRepository.save(generalPercentage);
        this.userDataService.update(userData.getId(), userData);
    }

    @Override
    public PLanguage findFavouriteLanguageForUserData(UserData userData) {
        return this.getMostUsedLanguage(userData.getId());
    }

    @Override
    public Integer getSubmissionsPassedPercentage(UserData userData) {
        Long numberAllSubmissions       = this.generalPercentageRepository.countAllSubmissionsByUserData(userData.getId());
        Long numberPassedSubmissions    = this.generalPercentageRepository.countPassedSubmissionsByUserData(userData.getId());

        return getPercentage(numberPassedSubmissions, numberAllSubmissions);
    }

    @Override
    public Integer getChallengesPassedPercentage(UserData userData) {
        Long numberAllChallenges        = this.generalPercentageRepository.countAllChallengesByUserDataId(userData.getId());
        Long numberPassedChallenges     = this.generalPercentageRepository.countAllPassedChallengesByUserDataId(userData.getId());

        return getPercentage(numberPassedChallenges, numberAllChallenges);
    }

    private Integer getPercentage(Long a, Long b) {
        double percentage =  (double) a / (double) b;
        percentage *= 100;
        return Integer.valueOf((int) percentage);
    }

    public PLanguage getMostUsedLanguage(Long userDataId) {
        TypedQuery<PLanguage> query =
                em.createQuery("select p from PLanguage p inner join Submission s on s.language.id = p.id inner join UserData ud on ud.id = s.userData.id group by (p) order by count (p) DESC", PLanguage.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
