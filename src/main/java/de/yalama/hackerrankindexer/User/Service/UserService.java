package de.yalama.hackerrankindexer.User.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import java.security.NoSuchAlgorithmException;
import java.util.Set;

public abstract class UserService implements BaseService<User> {

    public abstract PLanguage getFavouriteLanguage(User user);

    public abstract double getGeneralSubmissionPassPercentage(User user);

    public abstract double getGeneralChallengePassPercentage(User user);

    public abstract User findByEmail(String email);

    /**
     * returns all submission instances linked to User that have the specified language as language
     * @param user The user instance of the submission
     * @param language The wanted langauge
     * @return All submissions that of user that have specified language
     */
    public abstract Set<Submission> findSubmissionsOfUserOfLanguage(User user, PLanguage language);

    /**
     * An alternative to BaseService::save because NoSuchAlgorithmException needs to be thrown
     * in persisting users only in userService
     * What happens is the user is assigned his tokens and password hash then BaseService::save is called
     * Email for user is sent
     * @param user The user to persist
     * @return the persisted User instance
     */
    public abstract User register(User user) throws HackerrankIndexerException, NoSuchAlgorithmException;
}
