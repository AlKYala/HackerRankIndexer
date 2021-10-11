package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AnalyticsServiceImpl extends AnalyticsService {

    private SubmissionService submissionService;
    private ChallengeService challengeService;
    private PLanguageService pLanguageService;

    private Map<Long, Double> percentageSubmissionsBySessionId;

    private Map<Long, Double> percentageChallengesbySessionId;

    private Map<Long, Map<Long, Double>> percentageByLanguageIdBySessionId; //SessionId is outer dimension

    private Map<Long, Map<Long, Double>> percentageByChallengeIdBySessionId;

    private Map<Long, UsageStatistics> usageStatisticsBySessionId;

    private Map<Long, PassPercentages> passPercentagesBySessionId;

    private Map<Long, PLanguage> favouriteBySessionId;

    public AnalyticsServiceImpl(SubmissionService submissionService, ChallengeService challengeService,
                                PLanguageService pLanguageService) {
        this.pLanguageService = pLanguageService;
        this.submissionService = submissionService;
        this.challengeService = challengeService;
        this.percentageSubmissionsBySessionId = new HashMap<Long, Double>();
        this.percentageChallengesbySessionId = new HashMap<Long, Double>();
        this.percentageByLanguageIdBySessionId = new HashMap<Long, Map<Long, Double>>();
        this.percentageByChallengeIdBySessionId = new HashMap<Long, Map<Long, Double>>();
        this.usageStatisticsBySessionId = new HashMap<Long, UsageStatistics>();
        this.passPercentagesBySessionId = new HashMap<Long, PassPercentages>();
        this.favouriteBySessionId = new HashMap<Long, PLanguage>();
    }

    @Override
    public Double getPercentagePassedSubmissions(long sessionId) {
        if (!percentageSubmissionsBySessionId.containsKey(sessionId)) {
            int passed = this.submissionService.getAllPassed(sessionId).size();
            int total = this.submissionService.findAllBySessionId(sessionId).size();
            double percentageForSessionId = ((double) passed) / ((double) total);
            this.percentageSubmissionsBySessionId.put(sessionId, percentageForSessionId);
        }
        return this.percentageSubmissionsBySessionId.get(sessionId);
    }

    @Override
    public Double getPercentagePassedChallenges(long sessionId) {
        if (!percentageChallengesbySessionId.containsKey(sessionId)) {
            int passed = this.challengeService.getAllPassedChallengesBySessionId(sessionId).size();
            int total = this.challengeService.getAllChallengesBySessionId(sessionId).size();
            double percentageChallengesForSessionId = ((double) passed) / ((double) total);
            this.percentageChallengesbySessionId.put(sessionId, percentageChallengesForSessionId);
        }
        return this.percentageChallengesbySessionId.get(sessionId);
    }

    @Override
    public Double getPercentagePassedByLanguage(Long languageId, long sessionId) {
        if (!this.checkLanguagePercentageForSessionIdExists(sessionId, languageId)) {
            AtomicInteger passed = new AtomicInteger(0);

            List<Submission> submissionsBySessionIdWithLanguage =
                    this.pLanguageService.findById(languageId)
                            .getSubmissions().stream()
                            .filter(submission -> submission.getSessionId() == sessionId)
                            .collect(Collectors.toList());

            int total = submissionsBySessionIdWithLanguage.size();

            submissionsBySessionIdWithLanguage
                    .forEach(submission -> this.incrementBySubmissionScore(submission.getScore(), passed));

            this.addPercentage(languageId, sessionId, passed.get(), total, this.percentageByLanguageIdBySessionId);
        }
        return this.percentageByLanguageIdBySessionId.get(sessionId).get(languageId);
    }

    private boolean checkLanguagePercentageForSessionIdExists(long sessionId, long languageId) {
        if(this.percentageByLanguageIdBySessionId.containsKey(sessionId)) {
            if(this.percentageByLanguageIdBySessionId.get(sessionId).containsKey(languageId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clearEverythingBySessionId(long sessionId) {
        this.percentageSubmissionsBySessionId.remove(sessionId);
        this.percentageChallengesbySessionId.remove(sessionId);
        this.percentageByLanguageIdBySessionId.remove(sessionId);
        this.percentageByChallengeIdBySessionId.remove(sessionId);
        this.usageStatisticsBySessionId.remove(sessionId);
        this.passPercentagesBySessionId.remove(sessionId);
        this.favouriteBySessionId.remove(sessionId);
    }

    @Override
    public UsageStatistics getUsagePercentagesBySessionId(long sessionId) {
        if (!this.usageStatisticsBySessionId.containsKey(sessionId)) {
            this.createUsagePercentages(sessionId);
        }
        return this.usageStatisticsBySessionId.get(sessionId);
    }


    private void createUsagePercentages(long sessionId) {
        this.usageStatisticsBySessionId.put(sessionId, new UsageStatistics());
        this.pLanguageService
                .findPLanguagesUsedBySessionId(sessionId)
                .forEach(pLanguage -> this.addPLanguageToUsageStatistics(pLanguage, sessionId));
    }

    private void addPLanguageToUsageStatistics(PLanguage pLanguage, long sessionId) {
        int numSubmission = (int) pLanguage.getSubmissions()
                .stream()
                .filter(submission -> submission.getSessionId() == sessionId).count();
        this.usageStatisticsBySessionId.get(sessionId).getPLanguages().add(pLanguage);
        this.usageStatisticsBySessionId.get(sessionId).getNumberSubmissions().add(numSubmission);
    }

    @Override
    public PassPercentages getPassPercentages(long sessionId) {

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

    private void addPLanguageToPassPercentages(PLanguage pLanguage, long sessionId) {
        //log.info("Adding: {} to passPercentages", pLanguage);
        //TODO irgendwie gibts hier ein problem

        //Hierran liegts nicht
        if (!this.passPercentagesBySessionId.containsKey(sessionId)) {
            this.passPercentagesBySessionId = new HashMap<Long, PassPercentages>();
        }
        //hieran?
        this.createPassPercentageData(pLanguage, sessionId);
        /*log.info("Pass percentages{}\n{}", this.passPercentagesBySessionId.get(sessionId).getPercentages()
            ,this.passPercentagesBySessionId.get(sessionId).getPLanguages());*/
    }

    private void createPassPercentageData(PLanguage pLanguage, long sessionId) {
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
    public PLanguage getFavouriteLanguage(long sessionId) {
        if (!this.favouriteBySessionId.containsKey(sessionId)) {
            PLanguage favoriteBySessionId = this.findFavouriteLanguageFromUsagePercentages(sessionId);
            this.favouriteBySessionId.put(sessionId, favoriteBySessionId);
        }
        return this.favouriteBySessionId.get(sessionId);
    }

    private PLanguage findFavouriteLanguageFromUsagePercentages(long sessionId) {
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

    private double findPercentageForPLanguage(PLanguage pLanguage, long sessionId, int total) {
        long numSubmissionsByLanguageAndSessionId = pLanguage.getSubmissions()
                .stream()
                .filter(submission -> submission.getSessionId() == sessionId)
                .count();
        return this.roundToDecimal(((double) numSubmissionsByLanguageAndSessionId) / ((double) total), 4);
    }

    private double findPercentageForPLanguage(Long pLanguageId, long sessionId, int total) {
        return this.findPercentageForPLanguage(this.pLanguageService.findById(pLanguageId), sessionId, total);
    }

    //TODO remodel parameter
    @Override
    public boolean checkSubmissionsExistBySessionId(long sessionId) {

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
    private void addPercentage(long id, long sessionId, int passed, int total, Map<Long, Map<Long, Double>> map) {
        double percentage = ((double) passed) / ((double) total);
        percentage = this.roundToDecimal(percentage, 2);
        map.get(sessionId).put(id, percentage);
    }

    private void addPassPercentage(PLanguage pLanguage, long sessionId, int passed, int total) {
        double percentage = ((double) passed) / ((double) total);
        System.out.printf("amogus %f\n", percentage);
        this.passPercentagesBySessionId.get(sessionId).getPercentages().add(this.roundToDecimal(percentage, 4));
    }

    private Double roundToDecimal(double value, int places) {
        BigDecimal rounder = BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP);
        return rounder.doubleValue();
    }
}
