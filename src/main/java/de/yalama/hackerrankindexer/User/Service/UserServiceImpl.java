package de.yalama.hackerrankindexer.User.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Repository.UserRepository;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.ServiceHandler;
import de.yalama.hackerrankindexer.shared.services.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserServiceImpl extends UserService {

    private UserRepository userRepository;
    private Validator<User, UserRepository> validator;
    private ServiceHandler<User, UserRepository> serviceHandler;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.validator = new Validator<User, UserRepository>("User", this.userRepository);
        this.serviceHandler = new ServiceHandler<User, UserRepository>(this.userRepository, this.validator);
    }

    @Override
    public User findById(Long id) throws HackerrankIndexerException {
        return this.serviceHandler.findById(id);
    }

    @Override
    public List<User> findAll() throws HackerrankIndexerException {
        return this.serviceHandler.findAll();
    }

    @Override
    public User save(User instance) throws HackerrankIndexerException {
        instance.setPasswordHashed(this.passwordEncoder.encode(instance.getPasswordHashed()));
        return this.serviceHandler.save(instance);
    }

    @Override
    public User update(Long id, User instance) throws HackerrankIndexerException {
        return this.serviceHandler.update(id, instance);
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, 1);
        this.findById(id).getSubmittedEntries().forEach(submission -> submission.setWriter(null));
        return this.serviceHandler.deleteById(id);
    }

    //TODO
    @Override
    public PLanguage getFavouriteLanguage() {
        return null;
    }

    //TODO
    @Override
    public double getGeneralSubmissionPassPercentage() {
        return 0;
    }

    //TODO
    @Override
    public double getGeneralChallengePassPercentage() {
        return 0;
    }

    @Override
    public User findByUsername(String username) {
        log.info("search: {}", username);
        User found = this.findAll()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny()
                .get();
        if(found == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return found;
    }
}
