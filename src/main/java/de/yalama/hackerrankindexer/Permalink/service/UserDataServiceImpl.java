package de.yalama.hackerrankindexer.Permalink.service;

import de.yalama.hackerrankindexer.Permalink.Model.UserData;
import de.yalama.hackerrankindexer.Security.service.EncodeDecodeService;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Repository.UserRepository;
import de.yalama.hackerrankindexer.shared.HashingAlgorithms.HashingAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class UserDataServiceImpl extends UserDataService {

    BCryptPasswordEncoder   bCryptPasswordEncoder;
    EncodeDecodeService     encodeDecodeService;

    public UserDataServiceImpl(EncodeDecodeService encodeDecodeService, UserRepository userRepository) {
        this.bCryptPasswordEncoder  = new BCryptPasswordEncoder();
        this.encodeDecodeService    = encodeDecodeService;
    }

    @Override
    public String getUserDataLinkForUser(User user) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException,
            InvalidKeyException, IOException {

        String env          = "localhost:8080"; //TODO
        String controller   = "permalink";

        if(user.getUserDataToken() != null && user.getUserDataToken().length() > 0) {
            return String.format("%s/%s/%s", env, controller, user.getUserDataToken());
        }

        String salt         = Integer.toString(user.hashCode());
        String key          = String.format("%s%s", user.getEmail(), salt);
        String arg          =  encodeDecodeService.hashValue(key, HashingAlgorithm.SHA256);

        while(arg.contains("/")) {
            arg = encodeDecodeService.hashValue(key, HashingAlgorithm.SHA256);
        }

        return String.format("%s/%s/%s", env, controller, arg);
    }
}
