package de.yalama.hackerrankindexer.GeneralPercentage.Service;

import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.GeneralPercentage.Repository.GeneralPercentageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.UserData.Service.UserDataService;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.validator.Validator;
import de.yalama.hackerrankindexer.shared.services.validator.ValidatorOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@Slf4j
public class GeneralPercentageServiceImpl extends GeneralPercentageService {


    private GeneralPercentageRepository generalPercentageRepository;
    private UserDataService             userDataService;
    private EntityManager               em;
    private Validator<GeneralPercentage, GeneralPercentageRepository> validator;

    public GeneralPercentageServiceImpl(UserDataService userDataService,
                                        GeneralPercentageRepository generalPercentageRepository,
                                        EntityManager entityManager) {
        this.generalPercentageRepository = generalPercentageRepository;
        this.em = entityManager;
        this.userDataService = userDataService;
        this.validator = new Validator<>("GeneralPercentage", generalPercentageRepository);
    }

    @Override
    public void calculatePercentageForUserData(UserData userData) {

        GeneralPercentage found = userData.getGeneralPercentage();

        if(found != null && found.isCalculated() && found.getFavouriteLanguage() != null) {
            return;
        }

        PLanguage favoritePLanguage = this.findFavouriteLanguageForUserData(userData);

        GeneralPercentage generalPercentage = new GeneralPercentage();

        generalPercentage.setUserData(userData);

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
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public GeneralPercentage findById(Long id) throws HackerrankIndexerException {
        return this.generalPercentageRepository.findById(id).get();
    }

    @Override
    public List<GeneralPercentage> findAll() throws HackerrankIndexerException {
        return this.generalPercentageRepository.findAll();
    }

    @Override
    public GeneralPercentage save(GeneralPercentage instance) throws HackerrankIndexerException {
        return this.generalPercentageRepository.save(instance);
    }

    @Override
    public GeneralPercentage update(Long id, GeneralPercentage instance) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, ValidatorOperations.SAVE);
        return this.generalPercentageRepository.save(instance);
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        this.generalPercentageRepository.deleteById(id);
        return id;
    }
}
