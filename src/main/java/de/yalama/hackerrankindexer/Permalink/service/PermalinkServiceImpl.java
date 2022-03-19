package de.yalama.hackerrankindexer.Permalink.service;

import de.yalama.hackerrankindexer.Security.SecurityConstants;
import de.yalama.hackerrankindexer.Security.service.EncodeDecodeService;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class PermalinkServiceImpl extends PermalinkService{

    BCryptPasswordEncoder   bCryptPasswordEncoder;
    EncodeDecodeService     encodeDecodeService;
    UserService             userService;

    public PermalinkServiceImpl(EncodeDecodeService encodeDecodeService, UserService userService) {
        this.bCryptPasswordEncoder  = new BCryptPasswordEncoder();
        this.encodeDecodeService    = encodeDecodeService;
        this.userService            = userService;
    }


    @Override
    public User resolveUserFromLink(String val) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException,
            BadPaddingException, InvalidKeyException {
        String valDecoded = encodeDecodeService.decrypt(val);
        int length = valDecoded.length();
        String email = valDecoded.substring(0, length-10);
        return this.userService.findByEmail(email);
    }

    @Override
    public String getPermalinkForUser(User user) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException,
            InvalidKeyException {
        String salt         = SecurityConstants.SECRET_KEY.substring(0, 10);
        String key          = String.format("%s%s", user.getEmail(), salt);
        String arg          =  encodeDecodeService.encryptString(key);
        String env          = "localhost:8080"; //TODO
        String controller   = "TODO"; //TODO
        return String.format("%s/%s/5s", env, controller, arg);
    }
}
