package de.yalama.hackerrankindexer.User.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Security.service.JwtService;
import de.yalama.hackerrankindexer.Security.service.TokenGenerationService;
import de.yalama.hackerrankindexer.Security.service.UserVerificationService;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Repository.UserRepository;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.exceptions.VerificationFailedException;
import de.yalama.hackerrankindexer.shared.services.EmailSendService;
import de.yalama.hackerrankindexer.shared.services.ServiceHandler;
import de.yalama.hackerrankindexer.shared.services.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl extends UserService {

    private UserRepository userRepository;
    private Validator<User, UserRepository> validator;
    private ServiceHandler<User, UserRepository> serviceHandler;
    private PasswordEncoder passwordEncoder;
    private EmailSendService emailSendService;
    private TokenGenerationService tokenGenerationService;
    private UserDetailsService userDetailsService;
    private JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           EmailSendService emailSendService, TokenGenerationService tokenGenerationService,
                           UserDetailsService userDetailsService, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.validator = new Validator<User, UserRepository>("User", this.userRepository);
        this.serviceHandler = new ServiceHandler<User, UserRepository>(this.userRepository, this.validator);
        this.emailSendService = emailSendService;
        this.tokenGenerationService = tokenGenerationService;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
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
        return this.serviceHandler.save(instance);
    }

    @Override
    public User register(User instance) throws HackerrankIndexerException, NoSuchAlgorithmException {
        instance.setPasswordHashed(this.passwordEncoder.encode(instance.getPasswordHashed()));
        instance.setToken(this.tokenGenerationService.generateVerificationToken(instance));
        this.emailSendService.sendConfirmationEmail(instance);
        return this.save(instance);
    }

    @Override
    public User setNewPassword(String token) {

        //TODO Check for expiration of token

        //TODO Extract User ID

        /*user = this.findById(user.getId());

        user.setPasswordHashed(this.passwordEncoder.encode(user.getPasswordHashed()));
        return this.update(user.getId(), user);*/
    }

    @Override
    public String triggerPasswordReset(User user) {


        String resetToken = this.generatePasswordResetToken(user);

        //TODO send Email
        this.emailSendService.sendPasswordResetMail(user, resetToken);

        return "Email reset password sent. Check your inbox";
    }

    private String generatePasswordResetToken(User user) {

        Map<String, Object> claims = new HashMap<String, Object>();

        claims.put("email", user.getEmail());
        claims.put("id", user.getId());
        claims.put("isPasswordReset", true);

        return this.jwtService.createCustomToken(claims);
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

    @Override
    public PLanguage getFavouriteLanguage(User user) {
        long max = -1;
        PLanguage favourite = null;
        log.info("looking for favouriote");
        for(UsagePercentage usagePercentage: user.getUsagePercentages()) {
            log.info("user {}: {} . {}", user.getId(), usagePercentage.getPLanguage().toString(), usagePercentage.getTotal());
            if(usagePercentage.getTotal() > max) {
                max = usagePercentage.getTotal();
                favourite = usagePercentage.getPLanguage();
            }
        }
        return favourite;
    }

    @Override
    public double getGeneralSubmissionPassPercentage(User user) {
        return user.getGeneralPercentage().getPercentageSubmissionsPassed();
    }

    @Override
    public double getGeneralChallengePassPercentage(User user) {
        return user.getGeneralPercentage().getPercentageChallengesSolved();
    }

    @Override
    public User findByEmail(String email) {
        log.info("search: {}", email);
        User found = this.findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny()
                .get();
        if(found == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return found;
    }

    @Override
    public Set<Submission> findSubmissionsOfUserOfLanguage(User user, PLanguage language) {
        return user.getSubmittedEntries()
                .stream()
                .filter(submission -> submission.getLanguage().getId() == language.getId())
                .collect(Collectors.toSet());
    }


}
