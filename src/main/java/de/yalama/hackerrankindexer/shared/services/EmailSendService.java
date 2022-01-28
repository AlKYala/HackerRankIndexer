package de.yalama.hackerrankindexer.shared.services;

import de.yalama.hackerrankindexer.User.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hello@hello.com"); //see application.properties
        message.setSubject("Your registration with Hackerrank Indexer");
        message.setTo(user.getEmail());
        String domain = "localhost:8080";
        String confirmAdress = String.format("%s/verify/%s", domain, user.getToken()); //TODO;

        String messageBody = String.format("Thanks for signing up at Hackerrank Indexer\nTo complete your registration click on this link:\n%s\nregards,\nHackerrank Indexer Team", confirmAdress);

        message.setText(messageBody);

        this.mailSender.send(message);
    }

    public void sendDummyMail() {
        User user = new User();
        user.setToken("blablabla");
        user.setEmail("blablabla@blablabla.com");
        user.setVerified(false);
        user.setId(100l);
        this.sendEmail(user);
    }
}
