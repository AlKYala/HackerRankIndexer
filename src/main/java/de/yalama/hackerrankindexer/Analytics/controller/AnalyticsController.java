package de.yalama.hackerrankindexer.Analytics.controller;


import de.yalama.hackerrankindexer.Analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/clear")
    public void clearStatistics() {
        this.analyticsService.clear();
    }

    @GetMapping("/submissions/passed")
    public double getPercentagePassedSubmissions() {
        return this.analyticsService.getPercentagePassedSubmissions();
    }

    @GetMapping("/challenge/passed")
    public double getPercentagePassedChallenges() {
        return this.analyticsService.getPercentagePassedChallenges();
    }

    @GetMapping("/pLanguage/{languageId}/passed")
    public double getPercentageOfPassedByLanguageId(@PathVariable Long languageId) {
        return this.analyticsService.getPercentagePassedByLanguage(languageId);
    }

    /* TODO find out why this is not working
    @GetMapping("/challenge/({challengeId}/percentages")

    public double getPercentageOfPassedByChallengeId(@PathVariable Long challengeId) {
        System.out.println("pinging");
        return this.analyticsService.getPercentagePassedByChallenge(challengeId);
    }*/

    @GetMapping("/pLanguage/percentages")
    public Map<Long, Double> getPercentagesOfPLanguages() {
        return this.analyticsService.getUsagePercentages();
    }
}
