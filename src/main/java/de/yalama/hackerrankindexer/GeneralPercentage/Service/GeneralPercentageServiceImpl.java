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

    private UserService userService;
    private GeneralPercentageRepository generalPercentageRepository;
    private ChallengeService challengeService;
    private SubmissionService submissionService;
    private PLanguageRepository pLanguageRepository;
    private EntityManager em;

    public GeneralPercentageServiceImpl(UserService userService,
                                        GeneralPercentageRepository generalPercentageRepository,
                                        SubmissionService submissionService, ChallengeService challengeService,
                                        EntityManager entityManager, PLanguageRepository pLanguageRepository) {
        this.userService = userService;
        this.generalPercentageRepository = generalPercentageRepository;
        this.submissionService = submissionService;
        this.challengeService = challengeService;
        this.em = entityManager;
        this.pLanguageRepository = pLanguageRepository;
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

        GeneralPercentage found = userData.getGeneralPercentage();

        if(found != null && found.isCalculated() && found.getFavouriteLanguage() != null) {
            return;
        }
        //TODO again
    }

    @Override
    public PLanguage findFavouriteLanguageForUserData(UserData userData) {
        return null;
    }

    @Override
    public Integer getSubmissionsPassedPercentage(UserData userData) {
        Long numberAllSubmissions       = this.countAllSubmissionsByUserData(userData.getId());
        Long numberPassedSubmissions    = this.countPassedSubmissionsByUserData(userData.getId());

        double percentage =  (double) numberPassedSubmissions / (double) numberAllSubmissions;
        percentage *= 100;
        return Integer.valueOf((int) percentage);
    }

    private Long countAllSubmissionsByUserData(Long userDataId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Submission> submissionRoot = cq.from(Submission.class);

        cq.select(cb.count(cq.from(Submission.class))); //PREPARE COUNT FOR Submissions
        cq.where(
                cb.equal(submissionRoot.get("userDataId"), userDataId)
        );

        return em.createQuery(cq).getSingleResult();
    }

    private Long countPassedSubmissionsByUserData(Long userDataId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Submission> submissionRoot = cq.from(Submission.class);

        cq.select(cb.count(cq.from(Submission.class))); //PREPARE COUNT FOR Submissions
        cq.where(
                cb.and(
                        cb.equal(submissionRoot.get("userDataId"), userDataId),
                        cb.equal(submissionRoot.get("score"), 1)
                )
        );

        return em.createQuery(cq).getSingleResult();
    }

    @Override
    public Integer getChallengesPassedPercentage(UserData userData) {
        return null;
    }

    @Override
    public PLanguage getMostUsedLanguage(Long userDataId) {
        TypedQuery<PLanguage> query = em.createQuery("select p from PLanguage p inner join Submission s on s.language.id = p.id inner join UserData ud on ud.id = s.userData.id group by (p) order by count (p) DESC", PLanguage.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
