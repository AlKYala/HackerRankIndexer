package de.yalama.hackerrankindexer.Analytics.controller;

import de.yalama.hackerrankindexer.Analytics.SupportModels.ChartEntry;
import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentageChartData;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Analytics.service.AnalyticsService;
import de.yalama.hackerrankindexer.Session.Service.SessionService;
import de.yalama.hackerrankindexer.shared.models.PassData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;

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

    @GetMapping("/exists")
    public boolean checkSubmissionsExist(HttpSession httpSession) {
        return this.analyticsService.checkSubmissionsExistBySessionId(this.getSessionId(httpSession));
    }

    private String getSessionId(HttpSession httpSession) {
        return this.sessionService.getCurrentSessionId(httpSession);
    }

    @GetMapping("/passData/{id}")
    public PassData getPassDataForLanguage(@PathVariable Long id, HttpSession httpSession) {
        String sessionId = this.sessionService.getCurrentSessionId(httpSession);
        return this.analyticsService.getPassDataForLangauge(id, sessionId);
    }

    @GetMapping("/passData/all")
    public Collection<PassPercentageChartData> getPassDataForAllLanguages(HttpSession httpSession) {
        String sessionId = this.sessionService.getCurrentSessionId(httpSession);
        return this.analyticsService.getPassPercentageChartData(sessionId);
    }

    @GetMapping("/usageData/all")
    public Collection<ChartEntry> getUsageChartEntries(HttpSession httpSession) {
        String sessionId = this.sessionService.getCurrentSessionId(httpSession);
        return this.analyticsService.getUsageChartEntries(sessionId);
    }

    @GetMapping("/colors")
    public String[] getAnalyticsColors() {
        return this.analyticsService.getAnalyticsColors();
    }

    @GetMapping("/challenges/passed/number")
    public Integer getNumberChallengesPassed(HttpSession httpSession) {
        String sessionId = this.getSessionId(httpSession);
        return this.analyticsService.getNumberChallengesPassed(sessionId);
    }
}
