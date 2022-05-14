package de.yalama.hackerrankindexer.UserData.Service;

import de.yalama.hackerrankindexer.Security.service.EncodeDecodeService;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Repository.UserRepository;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.UserData.Repository.UserDataRepository;
import de.yalama.hackerrankindexer.shared.HashingAlgorithms.HashingAlgorithm;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
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
import java.util.List;

@Service
public class UserDataServiceImpl extends UserDataService {

    BCryptPasswordEncoder   bCryptPasswordEncoder;
    EncodeDecodeService     encodeDecodeService;
    UserDataRepository      userDataRepository;

    public UserDataServiceImpl(EncodeDecodeService encodeDecodeService,
                               UserRepository userRepository,
                               UserDataRepository userDataRepository) {
        this.bCryptPasswordEncoder  = new BCryptPasswordEncoder();
        this.encodeDecodeService    = encodeDecodeService;
        this.userDataRepository     = userDataRepository;
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

    @Override
    public List<UserData> findByUser(User user) {
        return this.userDataRepository.getByUser(user.getId());
    }

    @Override
    public UserData findById(Long id) throws HackerrankIndexerException {
        return this.userDataRepository.findById(id).get();
    }

    @Override
    public List<UserData> findAll() throws HackerrankIndexerException {
        return this.userDataRepository.findAll();
    }

    @Override
    public UserData save(UserData instance) throws HackerrankIndexerException {
        return this.userDataRepository.save(instance);
    }

    @Override
    public UserData update(Long id, UserData instance) throws HackerrankIndexerException {
        UserData toUpdate = this.findById(id);

        toUpdate.setGeneralPercentage(instance.getGeneralPercentage());
        toUpdate.setPassPercentages(instance.getPassPercentages());
        toUpdate.setUsagePercentages(instance.getUsagePercentages());

        toUpdate.setUsedPLanguages(instance.getUsedPLanguages());
        toUpdate.setSubmissionList(instance.getSubmissionList());

        return toUpdate;
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        this.userDataRepository.deleteById(id);
        return id;
    }
}
