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

    public void sendConfirmationEmail(User user) {
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

    public void sendPasswordResetMail(User user, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hello@hello.com"); //in dev
        message.setSubject("Your password reset request on Hackerrank Indexer");
        message.setTo(user.getEmail());

        StringBuilder sb = new StringBuilder();

        String baseUrl = "localhost:8080/user/updatePassword?token=";

        String resetLink = String.format("%s%s", baseUrl, token);

        sb.append(String.format("Hello %s,\n\n", user.getEmail()));
        sb.append(String.format("We've received a password reset request for your account\n"));
        sb.append(String.format("To reset your password, please click here: %s\n\n", resetLink));

        message.setText(sb.toString());

        System.out.println(sb.toString());

        this.mailSender.send(message);
    }

    public void sendDummyMail() {
        User user = new User();
        user.setToken("blablabla");
        user.setEmail("blablabla@blablabla.com");
        user.setVerified(false);
        user.setId(100l);
        this.sendConfirmationEmail(user);
    }
}
