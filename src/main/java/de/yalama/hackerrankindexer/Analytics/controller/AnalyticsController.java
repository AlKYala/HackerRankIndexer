package de.yalama.hackerrankindexer.Analytics.controller;


import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Analytics.service.AnalyticsService;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.Permalink.Model.UserData;
import de.yalama.hackerrankindexer.Security.service.HeaderService;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.models.UsageData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;

@RestController
@RequestMapping("analytics")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private HeaderService headerService;

    @PostMapping("/clear")
    public void clearStatistics(HttpServletRequest request) {
        this.analyticsService.clear(this.headerService.getUserFromHeader(request));
    }

    @GetMapping("/general")
    public GeneralPercentage getGeneralPercentages(HttpServletRequest request) {
        return this.analyticsService.getGeneralPercentages(this.resolveUser(request));
    }

    @GetMapping("/usagepercentages")
    public Set<UsageData> getUsagePercentages(HttpServletRequest request) {
        return this.analyticsService.getUsagePercentages(this.resolveUser(request));
    }

    @GetMapping("/passpercentages")
    public Set<PassPercentage> getPassPercentages(HttpServletRequest request) {
        return this.analyticsService.getPassPercentages(this.resolveUser(request));
    }

    @GetMapping("/exists")
    public boolean checkSubmissionsExist(HttpServletRequest request) {
        return this.analyticsService.checkSubmissionsExist(this.headerService.getUserFromHeader(request));
    }

    @GetMapping("/numberUsers")
    public Long getNumberOfUsers() {
        return this.analyticsService.getNumberOfUsers();
    }

    @GetMapping("/numberSubmissions")
    public Long getNumberOfSubmissions() {
        return this.analyticsService.getNumberOfSubmissions();
    }

    @GetMapping("/userData/{token}")
    public UserData getUserData(@PathVariable String token) throws InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        return this.analyticsService.getUserData(token);
    }

    private User resolveUser(HttpServletRequest request) {
        return this.headerService.getUserFromHeader(request);
    }
}
