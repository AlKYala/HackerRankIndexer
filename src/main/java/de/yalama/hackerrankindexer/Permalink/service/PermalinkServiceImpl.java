package de.yalama.hackerrankindexer.Permalink.service;

import de.yalama.hackerrankindexer.Permalink.Model.PermalinkInformation;
import de.yalama.hackerrankindexer.Security.SecurityConstants;
import de.yalama.hackerrankindexer.Security.service.EncodeDecodeService;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.shared.HashingAlgorithms.HashingAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class PermalinkServiceImpl extends PermalinkService {

    BCryptPasswordEncoder   bCryptPasswordEncoder;
    EncodeDecodeService     encodeDecodeService;
    UserService             userService;

    public PermalinkServiceImpl(EncodeDecodeService encodeDecodeService, UserService userService) {
        this.bCryptPasswordEncoder  = new BCryptPasswordEncoder();
        this.encodeDecodeService    = encodeDecodeService;
        this.userService            = userService;
    }


    @Override
    public PermalinkInformation resolveUserFromLink(String val) {
        User user = this.userService.findByPermalinkToken(val);
        return new PermalinkInformation(user);
    }

    @Override
    public String getPermalinkForUser(User user) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException,
            InvalidKeyException, IOException {

        String env          = "localhost:8080"; //TODO
        String controller   = "permalink";

        if(user.getPermalinkToken() != null && user.getPermalinkToken().length() > 0) {
            return String.format("%s/%s/%s", env, controller, user.getPermalinkToken());
        }

        String salt         = Integer.toString(user.hashCode());
        String key          = String.format("%s%s", user.getEmail(), salt);
        String arg          =  encodeDecodeService.hashValue(key, HashingAlgorithm.SHA256);


        while(arg.contains("/")) {
            arg = encodeDecodeService.hashValue(key, HashingAlgorithm.SHA256);
        }

        user.setPermalinkToken(arg);
        this.userService.update(user.getId(), user);

        return String.format("%s/%s/%s", env, controller, arg);
    }
}
