package de.yalama.hackerrankindexer.Analytics.controller;


import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Analytics.service.AnalyticsService;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Security.service.HeaderService;
import de.yalama.hackerrankindexer.User.Model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
        log.info(request.getHeader("Authorization"));
        User user = this.headerService.getUserFromHeader(request);
        return user.getGeneralPercentage();
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
}
