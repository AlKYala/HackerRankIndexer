package de.yalama.hackerrankindexer.Analytics.controller;


import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Analytics.service.AnalyticsService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Session.Service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private SessionService sessionService;

    private Long sessionId;

    @PostMapping("/clear")
    public void clearStatistics(HttpSession httpSession) {
        this.analyticsService.clearEverythingBySessionId(this.getSessionId(httpSession));
    }

    @GetMapping("/submissions/passed")
    public double getPercentagePassedSubmissions(HttpSession httpSession) {
        log.info("{}", this.analyticsService.getPercentagePassedSubmissions(this.getSessionId(httpSession)));
        return this.analyticsService.getPercentagePassedSubmissions(this.getSessionId(httpSession));
    }

    @GetMapping("/challenges/passed")
    public double getPercentagePassedChallenges(HttpSession httpSession) {
        log.info("{}", this.analyticsService.getPercentagePassedChallenges(this.getSessionId(httpSession)));
        return this.analyticsService.getPercentagePassedChallenges(this.getSessionId(httpSession));
    }

    @GetMapping("/pLanguage/{languageId}/passed")
    public double getPercentageOfPassedByLanguageId(@PathVariable Long languageId, HttpSession httpSession) {
        return this.analyticsService.getPercentagePassedByLanguage(languageId, this.getSessionId(httpSession));
    }

    @GetMapping("/pLanguage/percentages/usage")
    public UsageStatistics getUsagePercentagesOfPLanguages(HttpSession httpSession) {
        return this.analyticsService.getUsagePercentagesBySessionId(this.getSessionId(httpSession));
    }

    @GetMapping("/pLanguage/percentages/passed")
    public PassPercentages getPassPercentagesOfLanguages(HttpSession httpSession) {
        //debug
        // hier stimmts!
        /*PassPercentages p = this.analyticsService.getPassPercentages(this.getSessionId(httpServletRequest));
        System.out.printf("debug: %s\n", p.toString());*/

        return this.analyticsService.getPassPercentages(this.getSessionId(httpSession));
    }

    @GetMapping("/pLanguage/favourite")
    public PLanguage getFavouriteLanguage(HttpSession httpSession){
        //debug
        System.out.println(this.getSessionId(httpSession));
        log.info("{}", this.analyticsService.getFavouriteLanguage(this.getSessionId(httpSession)).getLanguage());
        return this.analyticsService.getFavouriteLanguage(this.getSessionId(httpSession));
    }

    @GetMapping("/exists")
    public boolean checkSubmissionsExist(HttpSession httpSession) {
        return this.analyticsService.checkSubmissionsExistBySessionId(this.getSessionId(httpSession));
    }

    private String getSessionId(HttpSession httpSession) {
        return this.sessionService.getCurrentSessionId(httpSession);
    }
}
