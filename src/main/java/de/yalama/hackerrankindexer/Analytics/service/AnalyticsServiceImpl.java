package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Analytics.UsagePercentages;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private Map<Long,Double> percentageByChallengeId;
    private UsagePercentages usagePercentages;

    public AnalyticsServiceImpl(SubmissionService submissionService, ChallengeService challengeService,
                                PLanguageService pLanguageService) {
        this.pLanguageService = pLanguageService;
        this.submissionService = submissionService;
        this.challengeService = challengeService;
        this.percentageByLanguageId = new HashMap<Long, Double>();
        this.percentageByChallengeId = new HashMap<Long, Double>();
        this.usagePercentages = new UsagePercentages();
    }

    @Override
    public Double getPercentagePassedSubmissions() {
        if(percentageSubmissions == null) {
            int passed = this.submissionService.getAllPassed().size();
            int total = this.submissionService.findAll().size();
            percentageSubmissions = ((double) passed) / ((double) total);
        }
        return percentageSubmissions;
    }

    @Override
    public Double getPercentagePassedChallenges() {
        if(percentageChallenges == null) {
            int passed = this.challengeService.getAllPassedChallenges().size();
            int total = this.challengeService.findAll().size();
            percentageChallenges = ((double) passed) / ((double) total);
        }
        return percentageChallenges;
    }

    @Override
    public Double getPercentagePassedByLanguage(Long languageId) {
        if(!this.percentageByLanguageId.containsKey(languageId)) {
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
        if(!this.percentageByChallengeId.containsKey(challengeId)) {
            AtomicInteger passed = new AtomicInteger(0);
            int total = this.challengeService.findById(challengeId).getSubmissions().size();
            this.challengeService.findById(challengeId)
                    .getSubmissions()
                    .forEach(submission ->
                            this.incrementBySubmissionScore(submission.getScore(), passed));
            this.addPercentage(challengeId, passed.get(), total, this.percentageByChallengeId);
        }
        log.info("%f", this.percentageByChallengeId.get(challengeId));
        return this.percentageByChallengeId.get(challengeId);
    }

    @Override
    public UsagePercentages getUsagePercentages() {
        if(this.usagePercentages.size() == 0) {
            this.usagePercentages = new UsagePercentages();
            int total = this.submissionService.findAll().size();
            this.pLanguageService
                    .findAll()
                    .forEach(pLanguage -> this.addPLanguageStatisticsToUsages(pLanguage, total));
        }
        return this.usagePercentages;
    }

    private void addPLanguageStatisticsToUsages(PLanguage pLanguage, int total) {
        this.usagePercentages.getPLanguages().add(pLanguage);
        this.usagePercentages.getUsagePercentages().add(this.findPercentageForPLanguage(pLanguage, total));
    }

    private double findPercentageForPLanguage(PLanguage pLanguage, int total) {
        return ((double) pLanguage.getSubmissions().size()) /((double) total);
    }

    private double findPercentageForPLanguage(Long id, int total) {
        return this.findPercentageForPLanguage(this.pLanguageService.findById(id), total);
    }

    @Override
    public void clear() {
        this.percentageSubmissions = null;
        this.percentageChallenges = null;
        this.percentageByChallengeId.clear();
        this.percentageByLanguageId.clear();
    }

    //side effects
    private void incrementBySubmissionScore(double score, AtomicInteger passed) {
        if(score == 1.0) {
            passed.incrementAndGet();
        }
    }

    //side effects
    private void addPercentage(long id, int passed, int total, Map<Long, Double> map) {
        double percentage = ((double) passed) / ((double) total);
        map.put(id, percentage);
    }
}
