package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class AnalyticsServiceImpl extends AnalyticsService {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PLanguageService pLanguageService;

    private Double percentageSubmissions;

    private Double percentageChallenges;

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
        AtomicLong passed = new AtomicLong(0);
        this.pLanguageService.findById(languageId)
                .getSubmissions()
                .forEach(submission
                        -> this.incrementBySubmissionScore(submission.getScore(), passed));
        return ((double) passed.get()) / ((double) pLanguageService.findById(languageId).getSubmissions().size());
    }

    @Override
    public Double getPercentagePassedByChallenge(Long challengeId) {
        AtomicLong passed = new AtomicLong(0);
        this.challengeService.findById(challengeId)
                .getSubmissions()
                .forEach(submission ->
                        this.incrementBySubmissionScore(submission.getScore(), passed));
        return ((double) passed.get()) / ((double) challengeService.findById(challengeId).getSubmissions().size());
    }

    @Override
    public void clear() {
        this.percentageSubmissions = null;
        this.percentageChallenges = null;
    }

    private void incrementBySubmissionScore(double score, AtomicLong passed) {
        if(score == 1.0) {
            passed.incrementAndGet();
        }
    }
}
