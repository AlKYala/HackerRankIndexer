package de.yalama.hackerrankindexer.Security.controller;

import de.yalama.hackerrankindexer.Security.service.UserVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/verify")
@RequiredArgsConstructor
public class UserVerficationController {

    @Autowired
    private UserVerificationService userVerificationService;

    @GetMapping("/{token}")
    public void verifyUser(@PathVariable String token) {
        this.userVerificationService.verifyUser(token);
    }
}
