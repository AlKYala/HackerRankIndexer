package de.yalama.hackerrankindexer.Analytics.controller;


import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Analytics.service.AnalyticsService;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.Security.service.HeaderService;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@RestController
@RequestMapping("analytics")
@RequiredArgsConstructor
@Slf4j
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private HeaderService headerService;

    @PostMapping("/clear")
    public void clearStatistics() {
        this.analyticsService.clear();
    }

    @GetMapping("/general")
    public GeneralPercentage getGeneralPercentages(HttpServletRequest request) {
        return this.analyticsService.getGeneralPercentages(this.resolveUser(request));
    }

    @GetMapping("/usagepercentages")
    public Set<UsagePercentage> getUsagePercentages(HttpServletRequest request) {
        return this.analyticsService.getUsagePercentages(this.resolveUser(request));
    }

    @GetMapping("/passpercentages")
    public Set<PassPercentage> getPassPercentages(HttpServletRequest request) {
        return this.analyticsService.getPassPercentages(this.resolveUser(request));
    }

    /*@GetMapping("/submissions/passed")
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
    }*/

    @GetMapping("/exists")
    public boolean checkSubmissionsExist() {
        return this.analyticsService.checkSubmissionsExist();
    }

    private User resolveUser(HttpServletRequest request) {
        return this.headerService.getUserFromHeader(request);
    }
}
