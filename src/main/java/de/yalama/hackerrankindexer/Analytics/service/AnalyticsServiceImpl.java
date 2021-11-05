package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.shared.models.PassData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AnalyticsServiceImpl extends AnalyticsService {

    private SubmissionService submissionService;
    private ChallengeService challengeService;
    private PLanguageService pLanguageService;

    private Map<String, Double> percentageSubmissionsBySessionId;

    private Map<String, Double> percentageChallengesbySessionId;

    private Map<String, Map<Long, Double>> percentageByLanguageIdBySessionId; //SessionId is outer dimension

    private Map<String, Map<Long, Double>> percentageByChallengeIdBySessionId;

    private Map<String, UsageStatistics> usageStatisticsBySessionId;

    private Map<String, PassPercentages> passPercentagesBySessionId;

    private Map<String, PLanguage> favouriteBySessionId;

    /**
     * sessionId : Map<LanguageId, Passdata>
     */
    private Map<String, Map<Long, PassData>> passDataInstances;

    public AnalyticsServiceImpl(SubmissionService submissionService, ChallengeService challengeService,
                                PLanguageService pLanguageService) {
        this.pLanguageService = pLanguageService;
        this.submissionService = submissionService;
        this.challengeService = challengeService;
        this.percentageSubmissionsBySessionId = new HashMap<String, Double>();
        this.percentageChallengesbySessionId = new HashMap<String, Double>();
        this.percentageByLanguageIdBySessionId = new HashMap<String, Map<Long, Double>>();
        this.percentageByChallengeIdBySessionId = new HashMap<String, Map<Long, Double>>();
        this.usageStatisticsBySessionId = new HashMap<String, UsageStatistics>();
        this.passPercentagesBySessionId = new HashMap<String, PassPercentages>();
        this.favouriteBySessionId = new HashMap<String, PLanguage>();
        this.passDataInstances = new HashMap<String, Map<Long, PassData>>();
    }

    @Override
    public Double getPercentagePassedSubmissions(String sessionId) {
        if (!percentageSubmissionsBySessionId.containsKey(sessionId)) {
            int passed = this.submissionService.getAllPassed(sessionId).size();
            int total = this.submissionService.findAllBySessionId(sessionId).size();
            double percentageForSessionId = ((double) passed) / ((double) total);
            this.percentageSubmissionsBySessionId.put(sessionId, percentageForSessionId);
        }
        return this.percentageSubmissionsBySessionId.get(sessionId);
    }

    @Override
    public Double getPercentagePassedChallenges(String  sessionId) {
        if (!percentageChallengesbySessionId.containsKey(sessionId)) {
            int passed = this.challengeService.getAllPassedChallengesBySessionId(sessionId).size();
            int total = this.challengeService.getAllChallengesBySessionId(sessionId).size();
            double percentageChallengesForSessionId = ((double) passed) / ((double) total);
            this.percentageChallengesbySessionId.put(sessionId, percentageChallengesForSessionId);
        }
        return this.percentageChallengesbySessionId.get(sessionId);
    }

    @Override
    public Double getPercentagePassedByLanguage(Long languageId, String sessionId) {
        if (!this.checkLanguagePercentageForSessionIdExists(sessionId, languageId)) {
            AtomicInteger passed = new AtomicInteger(0);

            List<Submission> submissionsBySessionIdWithLanguage =
                    this.pLanguageService.findById(languageId)
                            .getSubmissions().stream()
                            .filter(submission -> submission.getSessionId().equals(sessionId))
                            .collect(Collectors.toList());

            int total = submissionsBySessionIdWithLanguage.size();

            submissionsBySessionIdWithLanguage
                    .forEach(submission -> this.incrementBySubmissionScore(submission.getScore(), passed));

            this.addPercentage(languageId, sessionId, passed.get(), total, this.percentageByLanguageIdBySessionId);
        }
        return this.percentageByLanguageIdBySessionId.get(sessionId).get(languageId);
    }

    private boolean checkLanguagePercentageForSessionIdExists(String sessionId, long languageId) {
        if(this.percentageByLanguageIdBySessionId.containsKey(sessionId)) {
            if(this.percentageByLanguageIdBySessionId.get(sessionId).containsKey(languageId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clearEverythingBySessionId(String sessionId) {
        this.percentageSubmissionsBySessionId.remove(sessionId);
        this.percentageChallengesbySessionId.remove(sessionId);
        this.percentageByLanguageIdBySessionId.remove(sessionId);
        this.percentageByChallengeIdBySessionId.remove(sessionId);
        this.usageStatisticsBySessionId.remove(sessionId);
        this.passPercentagesBySessionId.remove(sessionId);
        this.favouriteBySessionId.remove(sessionId);
    }

    @Override
    public PassData getPassDataForLangauge(Long id, String sessionId) {
        return (this.passDataInstances.containsKey(sessionId) && this.passDataInstances.get(sessionId).containsKey(id)) ?
                this.passDataInstances.get(sessionId).get(id) : this.generatePassDataAndPersist(id, sessionId);
    }

    private PassData generatePassDataAndPersist(Long id, String sessionId) {
        if(!this.passDataInstances.containsKey(sessionId)) {
            this.passDataInstances.put(sessionId, new HashMap<Long, PassData>());
        }

        PassData passData = new PassData();
        PLanguage firstInstance = this.pLanguageService.findById(id);
        Integer passed = this.pLanguageService.getPassedSubmissionsForLanguage(id, sessionId).size();
        Integer failed = this.pLanguageService.getFailedSubmissionsForLanguage(id, sessionId).size();
        passData.setFailed(failed);
        passData.setPassed(passed);
        passData.setLanguageId(firstInstance.getId());
        passData.setLanguageName(firstInstance.getLanguage());

        this.passDataInstances.get(sessionId).put(id, passData);

        return passData;
    }

    @Override
    public UsageStatistics getUsagePercentagesBySessionId(String sessionId) {
        if (!this.usageStatisticsBySessionId.containsKey(sessionId)) {
            this.createUsagePercentages(sessionId);
        }
        return this.usageStatisticsBySessionId.get(sessionId);
    }


    private void createUsagePercentages(String sessionId) {
        this.usageStatisticsBySessionId.put(sessionId, new UsageStatistics());
        this.pLanguageService
                .findPLanguagesUsedBySessionId(sessionId)
                .forEach(pLanguage -> this.addPLanguageToUsageStatistics(pLanguage, sessionId));
    }

    private void addPLanguageToUsageStatistics(PLanguage pLanguage, String sessionId) {
        int numSubmission = (int) pLanguage.getSubmissions()
                .stream()
                .filter(submission -> submission.getSessionId().equals(sessionId)).count();
        this.usageStatisticsBySessionId.get(sessionId).getPLanguages().add(pLanguage);
        this.usageStatisticsBySessionId.get(sessionId).getNumberSubmissions().add(numSubmission);
    }

    @Override
    public PassPercentages getPassPercentages(String sessionId) {

        //debug
        //log.info("languages used by SessionId: {}", this.pLanguageService.findPLanguagesUsedBySessionId(sessionId));

        if (!this.passPercentagesBySessionId.containsKey(sessionId)) {
            //er findet alle Sprachen die von der Session ID genutzt werden
            this.pLanguageService
                    .findPLanguagesUsedBySessionId(sessionId)
                    .forEach(pLanguage -> this.addPLanguageToPassPercentages(pLanguage, sessionId));
        }
        //log.info("Pass percentages done: {}", this.passPercentagesBySessionId.get(sessionId));
        return this.passPercentagesBySessionId.get(sessionId);
    }

    private void addPLanguageToPassPercentages(PLanguage pLanguage, String sessionId) {
        //log.info("Adding: {} to passPercentages", pLanguage);
        //TODO irgendwie gibts hier ein problem

        //Hierran liegts nicht
        if (!this.passPercentagesBySessionId.containsKey(sessionId)) {
            this.passPercentagesBySessionId = new HashMap<String, PassPercentages>();
        }
        //hieran?
        this.createPassPercentageData(pLanguage, sessionId);
        /*log.info("Pass percentages{}\n{}", this.passPercentagesBySessionId.get(sessionId).getPercentages()
            ,this.passPercentagesBySessionId.get(sessionId).getPLanguages());*/
    }

    private void createPassPercentageData(PLanguage pLanguage, String sessionId) {
        PassPercentages passPercentagesOfSession = (this.passPercentagesBySessionId.containsKey(sessionId)) ?
                this.passPercentagesBySessionId.get(sessionId) :
                new PassPercentages();
        passPercentagesOfSession.getPLanguages().add(pLanguage);
        int total = this.submissionService.filterBySessionIdAndLanguageId(pLanguage.getId(), sessionId).size();
        int passed = (int) this.submissionService
                .filterBySessionIdAndLanguageId(pLanguage.getId(), sessionId)
                .stream()
                .filter(submission -> submission.getScore() == 1.0).count();


        this.passPercentagesBySessionId.put(sessionId, passPercentagesOfSession);


        this.addPassPercentage(pLanguage, sessionId, passed, total);

        //log.info("{}", passPercentagesOfSession.toString());
    }

    @Override
    public PLanguage getFavouriteLanguage(String sessionId) {
        if (!this.favouriteBySessionId.containsKey(sessionId)) {
            PLanguage favoriteBySessionId = this.findFavouriteLanguageFromUsagePercentages(sessionId);
            this.favouriteBySessionId.put(sessionId, favoriteBySessionId);
        }
        return this.favouriteBySessionId.get(sessionId);
    }

    private PLanguage findFavouriteLanguageFromUsagePercentages(String sessionId) {
        PLanguage favourite = null;
        double maxFavSize = 0;

        UsageStatistics usageStatisticsForId = this.getUsagePercentagesBySessionId(sessionId);

        for(int i = 0; i < usageStatisticsForId.getNumberSubmissions().size(); i++) {
            double tempSize = this.usageStatisticsBySessionId.get(sessionId).getNumberSubmissions().get(i);
            if(maxFavSize < tempSize) {
                maxFavSize = tempSize;
                favourite = this.usageStatisticsBySessionId.get(sessionId).getPLanguages().get(i);
            }
        }

        return favourite;
    }

    private double findPercentageForPLanguage(PLanguage pLanguage, String sessionId, int total) {
        long numSubmissionsByLanguageAndSessionId = pLanguage.getSubmissions()
                .stream()
                .filter(submission -> submission.getSessionId().equals(sessionId))
                .count();
        return this.roundToDecimal(((double) numSubmissionsByLanguageAndSessionId) / ((double) total), 4);
    }

    private double findPercentageForPLanguage(Long pLanguageId, String sessionId, int total) {
        return this.findPercentageForPLanguage(this.pLanguageService.findById(pLanguageId), sessionId, total);
    }

    //TODO remodel parameter
    @Override
    public boolean checkSubmissionsExistBySessionId(String sessionId) {

        log.info("Checking sessionId for: {}", sessionId);

        return this.submissionService.findAllBySessionId(sessionId).size() > 0;
    }

    //side effects
    private void incrementBySubmissionScore(double score, AtomicInteger passed) {
        if (score == 1.0) {
            passed.incrementAndGet();
        }
    }

    //side effects
    //TODO: make it so only usagePercentages uses this
    private void addPercentage(long id, String sessionId, int passed, int total, Map<String, Map<Long, Double>> map) {
        double percentage = ((double) passed) / ((double) total);
        percentage = this.roundToDecimal(percentage, 2);
        map.get(sessionId).put(id, percentage);
    }

    private void addPassPercentage(PLanguage pLanguage, String sessionId, int passed, int total) {
        double percentage = ((double) passed) / ((double) total);
        System.out.printf("amogus %f\n", percentage);
        this.passPercentagesBySessionId.get(sessionId).getPercentages().add(this.roundToDecimal(percentage, 4));
    }

    private Double roundToDecimal(double value, int places) {
        BigDecimal rounder = BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP);
        return rounder.doubleValue();
    }
}
