package de.yalama.hackerrankindexer.Analytics.controller;


import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Analytics.service.AnalyticsService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/challenges/passed")
    public double getPercentagePassedChallenges() {
        return this.analyticsService.getPercentagePassedChallenges();
    }

    @GetMapping("/pLanguage/{languageId}/passed")
    public double getPercentageOfPassedByLanguageId(@PathVariable Long languageId) {
        return this.analyticsService.getPercentagePassedByLanguage(languageId);
    }

    @GetMapping("/pLanguage/percentages/usage")
    public UsageStatistics getUsagePercentagesOfPLanguages() {
        return this.analyticsService.getUsagePercentages();
    }

    @GetMapping("/pLanguage/percentages/passed")
    public PassPercentages getPassPercentagesOfLanguages() {
        return this.analyticsService.getPassPercentages();
    }

    @GetMapping("/pLanguage/favourite")
    public PLanguage getFavouriteLanguage(){
        return this.analyticsService.getFavouriteLanguage();
    }

    @GetMapping("/exists")
    public boolean checkSubmissionsExist() {
        return this.analyticsService.checkSubmissionsExist();
    }
}
