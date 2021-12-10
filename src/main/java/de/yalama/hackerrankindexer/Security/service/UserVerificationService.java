package de.yalama.hackerrankindexer.Security.service;

import de.yalama.hackerrankindexer.Security.SecurityConstants;
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

    public String generateVerificationToken(User user) throws NoSuchAlgorithmException {
        byte[] userHashedSecure = this.generateBytes(user, "SHA-512");
        return this.bytesToString(userHashedSecure);
    }

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

    //https://www.javaguides.net/2020/02/java-sha-512-hash-with-salt-example.html
    private byte[] generateBytes(User user, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        messageDigest.update(salt);
        String userHash = Integer.toString(user.hashCode());
        return messageDigest.digest(userHash.getBytes(StandardCharsets.UTF_8));
    }

    private String bytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
