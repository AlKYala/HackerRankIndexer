package de.yalama.hackerrankindexer.UserData.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.UserData.Model.UserDataFlat;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class UserDataService implements BaseService<UserData> {

    /**
     * Returns a list of all userData instances submitted by user
     * @param user the user
     * @return A list of all userDataInstances
     */
    public abstract List<UserData> findByUser(User user);

    /**
     * returns all submission instances linked of language in userData
     * @param userData The userData instance of the submission
     * @param language The wanted langauge
     * @return All submissions that of user that have specified language
     */
    public abstract Collection<Submission> findSubmissionsOfUserOfPlanguage(UserData userData, PLanguage language);

    /**
     * Returns a userService Instance by token
     * @param token The token to identify the userData Instance with
     * @return UserData instance with matching token or else null
     */
    public abstract UserData findUserDataByToken(String token);

    /**
     * Returns how many user Data the user has + Date uploaded + token
     * @return
     */
    public abstract List<UserDataFlat> getUserDataFlat(User user);
}
