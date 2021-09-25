package de.yalama.hackerrankindexer.Analytics.controller;


import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Analytics.service.AnalyticsService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Session.Service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public void clearStatistics(HttpServletRequest httpServletRequest) {
        this.analyticsService.clearEverythingBySessionId(this.getSessionId(httpServletRequest));
    }

    @GetMapping("/submissions/passed")
    public double getPercentagePassedSubmissions(HttpServletRequest httpServletRequest) {
        return this.analyticsService.getPercentagePassedSubmissions(this.getSessionId(httpServletRequest));
    }

    @GetMapping("/challenges/passed")
    public double getPercentagePassedChallenges(HttpServletRequest httpServletRequest) {
        return this.analyticsService.getPercentagePassedChallenges(this.getSessionId(httpServletRequest));
    }

    @GetMapping("/pLanguage/{languageId}/passed")
    public double getPercentageOfPassedByLanguageId(@PathVariable Long languageId, HttpServletRequest httpServletRequest) {
        return this.analyticsService.getPercentagePassedByLanguage(languageId, this.getSessionId(httpServletRequest));
    }

    @GetMapping("/pLanguage/percentages/usage")
    public UsageStatistics getUsagePercentagesOfPLanguages(HttpServletRequest httpServletRequest) {
        return this.analyticsService.getUsagePercentagesBySessionId(this.getSessionId(httpServletRequest));
    }

    @GetMapping("/pLanguage/percentages/passed")
    public PassPercentages getPassPercentagesOfLanguages(HttpServletRequest httpServletRequest) {
        return this.analyticsService.getPassPercentages(this.getSessionId(httpServletRequest));
    }

    @GetMapping("/pLanguage/favourite")
    public PLanguage getFavouriteLanguage(HttpServletRequest httpServletRequest){
        return this.analyticsService.getFavouriteLanguage(this.getSessionId(httpServletRequest));
    }

    @GetMapping("/exists")
    public boolean checkSubmissionsExist(HttpServletRequest httpServletRequest) {
        return this.analyticsService.checkSubmissionsExistBySessionId(this.getSessionId(httpServletRequest));
    }

    private long getSessionId(HttpServletRequest httpServletRequest) {
        if(this.sessionId == null) {
            this.sessionId = this.sessionService.getCurrentSessionId(httpServletRequest);
        }
        return this.sessionId;
    }
}
