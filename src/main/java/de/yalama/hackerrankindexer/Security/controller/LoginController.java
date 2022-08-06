package de.yalama.hackerrankindexer.Security.controller;

import de.yalama.hackerrankindexer.Security.model.AuthenticationRequest;
import de.yalama.hackerrankindexer.Security.model.LogInResponse;
import de.yalama.hackerrankindexer.Security.service.JwtService;
import de.yalama.hackerrankindexer.Security.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static de.yalama.hackerrankindexer.Security.service.SecurityConstants.SIGN_IN_ENDPOINT;


@RestController
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtService jwtService;

    /**
     * checks if request matches with submitted data
     * if pass, checks if the email-password combination is ok
     * sends ok if data verified
     */
    @PostMapping(SIGN_IN_ENDPOINT)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        return this.loginService.createAuthenticationToken(authenticationRequest);
    }

    @GetMapping("/isLoginValid")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> checkIsLoggedIn(HttpServletRequest httpServletRequest) {
        System.out.println(this.loginService.checkisLoggedIn(httpServletRequest).toString());
        return this.loginService.checkisLoggedIn(httpServletRequest);
    }
}
