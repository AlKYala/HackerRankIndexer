package de.yalama.hackerrankindexer.Security.service;

import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.shared.exceptions.VerificationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class UserVerificationService {

    @Autowired
    private UserService userService;

    public User verifyUser(String token) {
        User user = this.userService
                .findAll()
                .stream()
                .filter(user1 -> user1.getToken() != null && user1.getToken().equals(token))
                .findFirst().get();
        if(user == null) {
            throw new VerificationFailedException("User could not be verified");
        }
        user.setVerified(true);
        user.setToken(null);
        this.userService.update(user.getId(), user);
        return user;
    }
}
