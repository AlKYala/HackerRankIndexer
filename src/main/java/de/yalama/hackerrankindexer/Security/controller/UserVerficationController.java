package de.yalama.hackerrankindexer.Security.controller;

import de.yalama.hackerrankindexer.Security.service.UserVerificationService;
import de.yalama.hackerrankindexer.shared.services.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/verify")
@RestController
@RequiredArgsConstructor
public class UserVerficationController {

    @Autowired
    private UserVerificationService userVerificationService;

    @Autowired
    private EmailSendService emailSendService;

    @GetMapping("/{token}")
    public void verifyUser(@PathVariable String token) {
        this.userVerificationService.verifyUser(token);
    }

    @GetMapping("/test")
    public void testEmail() {
        this.emailSendService.sendDummyMail();
    }
}
