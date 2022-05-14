package de.yalama.hackerrankindexer.User.Service;

import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.GeneralPercentage.Repository.GeneralPercentageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.UserData.Service.UserDataService;
import de.yalama.hackerrankindexer.Security.model.PasswordResetModel;
import de.yalama.hackerrankindexer.Security.service.JwtService;
import de.yalama.hackerrankindexer.Security.service.TokenGenerationService;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Repository.UserRepository;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.Email.EmailSendService;
import de.yalama.hackerrankindexer.shared.exceptions.NotFoundException;
import de.yalama.hackerrankindexer.shared.exceptions.VerificationFailedException;
import de.yalama.hackerrankindexer.shared.models.ResponseString;
import de.yalama.hackerrankindexer.shared.services.ServiceHandler;
import de.yalama.hackerrankindexer.shared.services.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    private JwtService jwtService;
    private SubmissionService submissionService;
    private ChallengeService challengeService;
    private GeneralPercentageRepository generalPercentageRepository;
    private UserDataService userDataService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           EmailSendService emailSendService, TokenGenerationService tokenGenerationService,
                           JwtService jwtService, SubmissionService submissionService,
                           ChallengeService challengeService, GeneralPercentageRepository generalPercentageRepository,
                           UserDataService userDataService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.validator = new Validator<User, UserRepository>("User", this.userRepository);
        this.serviceHandler = new ServiceHandler<User, UserRepository>(this.userRepository, this.validator);
        this.emailSendService = emailSendService;
        this.tokenGenerationService = tokenGenerationService;
        this.jwtService = jwtService;
        this.submissionService = submissionService;
        this.challengeService = challengeService;
        this.generalPercentageRepository = generalPercentageRepository;
        this.userDataService = userDataService;
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
    public User register(User instance) throws HackerrankIndexerException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            InvalidKeySpecException, BadPaddingException, IOException, InvalidKeyException {
        instance.setPasswordHashed(this.passwordEncoder.encode(instance.getPasswordHashed()));
        instance.setToken(this.tokenGenerationService.generateVerificationToken(instance));
        this.emailSendService.sendConfirmationEmail(instance);

        String userDataLink  = this.userDataService.getUserDataLinkForUser(instance);
        String userDataToken = this.resolvePermalinkToken(userDataLink);
        instance.setUserDataToken(userDataToken);

        return this.save(instance);
    }

    @Override
    public User setNewPassword(PasswordResetModel passwordResetModel) throws ValidationException {

        String emailFromToken = this.jwtService.extractAnyHeaderFromToken(passwordResetModel.getToken(), "email");

        if(!emailFromToken.equals(passwordResetModel.getEmail())) {
            throw new ValidationException("Email Token mismatch");
        }

        if(jwtService.isTokenExpired(passwordResetModel.getToken())) {
            throw new ValidationException("User Token is Expired, cannot set New Password!");
        }

        String passwordHashed = this.passwordEncoder.encode(passwordResetModel.getPassword());

        User user = this.findByEmail(passwordResetModel.getEmail());
        user.setPasswordHashed(passwordHashed);

        return this.update(user.getId(), user);
    }

    @Override
    public String triggerPasswordReset(String email) {

        User user = null;
        try {
           user = this.findByEmail(email);
        }
        catch (UsernameNotFoundException e) {
            return "Email not found, terminating";
        }

        String resetToken = this.generatePasswordResetToken(user);

        this.emailSendService.sendPasswordResetMail(user, resetToken);

        return "Email reset password sent. Check your inbox";
    }

    @Override
    public ResponseString verifyUser(String token) {
        User userToVerify = this.findAll().stream()
                .filter(user -> user.getToken().equals(token))
                .findFirst()
                .orElse(null);
        if(userToVerify == null) {
            String formattedMessage = String.format("User with token %s not found", token);
            log.error(formattedMessage, new VerificationFailedException(formattedMessage));
        }

        userToVerify.setVerified(true);

        this.update(userToVerify.getId(), userToVerify);

        return new ResponseString(String.format("User %s verified successfully", userToVerify.getEmail()));
    }

    @Override
    public User findByPermalinkToken(String token) {
        return this.findAll().stream()
                .filter(user -> user.getUserDataToken().equals(token))
                .findFirst()
                .get();
    }

    @Override
    public UserData getUserData(String userDataToken) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException,
            BadPaddingException, InvalidKeyException {
        User foundUser = this.findAll()
                .stream()
                .filter(user -> user.getUserDataToken().equals(userDataToken))
                .findFirst()
                .get();
        if(foundUser == null) {
            throw new NotFoundException(String.format("No User found by Token %s", userDataToken));
        }
        UserData userData = new UserData(foundUser);
        return userData;
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

        if(user.getGeneralPercentage() != null
                && user.getGeneralPercentage().getFavouriteLanguage() != null) {
            return user.getGeneralPercentage().getFavouriteLanguage();
        }

        long max = -1;
        PLanguage favourite = null;
        log.info("looking for favouriote");

        //TODO - er hat die usagePercentages noch nicht?

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
                .orElse(null);
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

    private String resolvePermalinkToken(String permalink) {
        String[] pathNodes = permalink.split("/");
        return pathNodes[pathNodes.length-1];
    }
}
