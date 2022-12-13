package de.yalama.hackerrankindexer.Analytics.controller;


import de.yalama.hackerrankindexer.Analytics.service.AnalyticsService;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.Security.service.HeaderService;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.models.UsageData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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

    /**
     * An endpoint to Check if a user has existing user data
     * @return If User from request header has exisiting user data
     */
    @GetMapping("/exists")
    public boolean checkSubmissionsExist(HttpServletRequest request) {
        User user = this.headerService.getUserFromHeader((javax.servlet.http.HttpServletRequest) request);
        return this.analyticsService.checkSubmissionsExist(user);
    }

    /**
     * Returns the total number of registered users
     * @return the total number of registered users
     */
    @GetMapping("/numberUsers")
    public Long getNumberOfUsers() {
        return this.analyticsService.getNumberOfUsers();
    }

    /**
     * @return The total number of saved submissions
     */
    @GetMapping("/numberSubmissions")
    public Long getNumberOfSubmissions() {
        return this.analyticsService.getNumberOfSubmissions();
    }
}
