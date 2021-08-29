package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnalyticsServiceImpl extends AnalyticsService {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private ChallengeService challengeService;

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
    public void clear() {
        this.percentageSubmissions = null;
        this.percentageChallenges = null;
    }
}
