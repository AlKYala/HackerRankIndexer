package de.yalama.hackerrankindexer.User.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.Security.model.PasswordResetModel;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.models.ResponseString;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Set;

public abstract class UserService implements BaseService<User> {

    public abstract User findByEmail(String email);

    /**
     * An alternative to BaseService::save because NoSuchAlgorithmException needs to be thrown
     * in persisting users only in userService
     * What happens is the user is assigned his tokens and password hash then BaseService::save is called
     * Email for user is sent
     * @param user The user to persist
     * @return the persisted User instance
     */
    public abstract User register(User user) throws HackerrankIndexerException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, IOException, InvalidKeyException;

    /**
     * Updates an exisiting user - overrides password
     * @param passwordResetModel an instance of PaswordResetModel that contains the info that is needed
     * @return The updated instance
     */
    public abstract User setNewPassword(PasswordResetModel passwordResetModel) throws ValidationException;

    /**
     * Triggers an update for password of given user.
     * When this is triggered but the user does not update his password, the user is still allowed to login
     * with exisiting credentials
     * @return A string as confirmation message
     */
    public abstract String triggerPasswordReset(String email);

    /**
     * Endpoint used to verify users
     * @param token String that is persisted when user is registered - used to match in verification
     * @return A message about the result
     */
    public abstract ResponseString verifyUser(String token);

    /**
     * Gets all UserData instances by User
     * @return A Collection of found userData instances
     */
    public abstract List<UserData> getUserData(User user) throws InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException,
            BadPaddingException, InvalidKeyException;
}
