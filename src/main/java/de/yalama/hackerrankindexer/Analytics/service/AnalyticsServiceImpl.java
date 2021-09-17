package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class AnalyticsServiceImpl extends AnalyticsService {

    private SubmissionService submissionService;
    private ChallengeService challengeService;
    private PLanguageService pLanguageService;

    private Double percentageSubmissions;
    private Double percentageChallenges;

    private Map<Long, Double> percentageByLanguageId;
    private Map<Long, Double> percentageByChallengeId;
    private UsageStatistics usageStatistics;
    private PassPercentages passPercentages;

    private PLanguage favourite;

    public AnalyticsServiceImpl(SubmissionService submissionService, ChallengeService challengeService,
                                PLanguageService pLanguageService) {
        this.pLanguageService = pLanguageService;
        this.submissionService = submissionService;
        this.challengeService = challengeService;
        this.percentageByLanguageId = new HashMap<Long, Double>();
        this.percentageByChallengeId = new HashMap<Long, Double>();
        this.usageStatistics = new UsageStatistics();
        this.passPercentages = new PassPercentages();
    }

    @Override
    public Double getPercentagePassedSubmissions() {
        if (percentageSubmissions == null) {
            int passed = this.submissionService.getAllPassed().size();
            int total = this.submissionService.findAll().size();
            percentageSubmissions = ((double) passed) / ((double) total);
        }
        return percentageSubmissions;
    }

    @Override
    public Double getPercentagePassedChallenges() {
        if (percentageChallenges == null) {
            int passed = this.challengeService.getAllPassedChallenges().size();
            int total = this.challengeService.findAll().size();
            percentageChallenges = ((double) passed) / ((double) total);
        }
        return percentageChallenges;
    }

    @Override
    public Double getPercentagePassedByLanguage(Long languageId) {
        if (!this.percentageByLanguageId.containsKey(languageId)) {
            AtomicInteger passed = new AtomicInteger(0);
            int total = this.pLanguageService.findById(languageId).getSubmissions().size();
            this.pLanguageService.findById(languageId)
                    .getSubmissions()
                    .forEach(submission
                            -> this.incrementBySubmissionScore(submission.getScore(), passed));
            this.addPercentage(languageId, passed.get(), total, this.percentageByLanguageId);
        }
        return this.percentageByLanguageId.get(languageId);
    }

    @Override
    public Double getPercentagePassedByChallenge(Long challengeId) {
        if (!this.percentageByChallengeId.containsKey(challengeId)) {
            AtomicInteger passed = new AtomicInteger(0);
            int total = this.challengeService.findById(challengeId).getSubmissions().size();
            this.challengeService.findById(challengeId)
                    .getSubmissions()
                    .forEach(submission ->
                            this.incrementBySubmissionScore(submission.getScore(), passed));
            this.addPercentage(challengeId, passed.get(), total, this.percentageByChallengeId);
        }
        return this.percentageByChallengeId.get(challengeId);
    }

    @Override
    public UsageStatistics getUsagePercentages() {
        if (this.usageStatistics.size() == 0) {
            this.createUsagePercentages();
        }
        return this.usageStatistics;
    }

    private void createUsagePercentages() {
        this.pLanguageService
                .findAll()
                .forEach(pLanguage -> this.addPLanguageToUsageStatistics(pLanguage));
    }

    @Override
    public PassPercentages getPassPercentages() {
        if (this.passPercentages.size() == 0) {
            this.pLanguageService.findAll().forEach(pLanguage -> this.addPassPercentageAndLanguage(pLanguage));
        }
        return this.passPercentages;
    }

    private void addPassPercentageAndLanguage(PLanguage pLanguage) {
        double percentage = this.roundToDecimal(this.getPercentagePassedByLanguage(pLanguage.getId()), 4);
        this.passPercentages.getPLanguages().add(pLanguage);
        this.passPercentages.getPercentages().add(percentage);
    }

    @Override
    public PLanguage getFavouriteLanguage() {
        if (this.favourite == null) {
            if (this.usageStatistics == null || this.usageStatistics.size() < 1) {
                this.usageStatistics = this.getUsagePercentages();
            }
            this.favourite = this.findFavouriteLanguageFromUsagePercentages();
        }
        return this.favourite;
    }

    private PLanguage findFavouriteLanguageFromUsagePercentages() {
        PLanguage favourite = null;
        double maxFavSize = 0;

        for (int i = 0; i < this.usageStatistics.getNumberSubmissions().size(); i++) {
            double tempSize = this.usageStatistics.getNumberSubmissions().get(i);
            if (maxFavSize < tempSize) {
                maxFavSize = tempSize;
                favourite = this.usageStatistics.getPLanguages().get(i);
            }
        }
        return favourite;
    }

    private void addPLanguageToUsageStatistics(PLanguage pLanguage) {
        log.info(pLanguage.getLanguage());
        int numSubmission = pLanguage.getSubmissions().size();
        this.usageStatistics.getPLanguages().add(pLanguage);
        this.usageStatistics.getNumberSubmissions().add(numSubmission);
    }

    private double findPercentageForPLanguage(PLanguage pLanguage, int total) {
        return this.roundToDecimal(((double) pLanguage.getSubmissions().size()) / ((double) total), 4);
    }

    private double findPercentageForPLanguage(Long id, int total) {
        return this.findPercentageForPLanguage(this.pLanguageService.findById(id), total);
    }

    @Override
    public void clear() {
        this.percentageSubmissions = null;
        this.percentageChallenges = null;
        this.favourite = null;
        this.usageStatistics.clear();
        this.passPercentages.clear();
        this.percentageByChallengeId.clear();
        this.percentageByLanguageId.clear();
    }

    @Override
    public boolean checkSubmissionsExist() {
        return !this.submissionService.findAll().isEmpty();
    }

    //side effects
    private void incrementBySubmissionScore(double score, AtomicInteger passed) {
        if (score == 1.0) {
            passed.incrementAndGet();
        }
    }

    //side effects
    private void addPercentage(long id, int passed, int total, Map<Long, Double> map) {
        double percentage = ((double) passed) / ((double) total);
        percentage = this.roundToDecimal(percentage, 2);
        map.put(id, percentage);
    }

    private Double roundToDecimal(double value, int places) {
        BigDecimal rounder = BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP);
        return rounder.doubleValue();
    }
}
