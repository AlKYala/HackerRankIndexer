package de.yalama.hackerrankindexer.Analytics.controller;


import de.yalama.hackerrankindexer.Analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
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
    public Double getPercentagePassedSubmissions() {
        return this.analyticsService.getPercentagePassedSubmissions();
    }

    @GetMapping("/challenge/passed")
    public Double getPercentagePassedChallenges() {
        return this.analyticsService.getPercentagePassedChallenges();
    }

    @GetMapping("/pLanguage/{languageId}/passed")
    public Double getPercentageOfPassedByLanguageId(@PathVariable Long languageId) {
        return this.analyticsService.getPercentagePassedByLanguage(languageId);
    }

    @GetMapping("/challenge({challengeId}/passed")
    public Double getPercentageOfPassedByChallengeId(@PathVariable Long challengeId) {
        return this.analyticsService.getPercentagePassedByChallenge(challengeId);
    }
}
