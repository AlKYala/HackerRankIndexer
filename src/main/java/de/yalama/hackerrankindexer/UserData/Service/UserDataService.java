package de.yalama.hackerrankindexer.UserData.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Set;

public abstract class UserDataService implements BaseService<UserData> {

    public abstract String getUserDataLinkForUser(User user) throws NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            InvalidKeySpecException, BadPaddingException, InvalidKeyException, IOException;

    public abstract List<UserData> findByUser(User user);

    /**
     * returns all submission instances linked of language in userData
     * @param user The user instance of the submission
     * @param language The wanted langauge
     * @return All submissions that of user that have specified language
     */
    public abstract Set<Submission> findSubmissionsOfUserOfLanguage(UserData userData, PLanguage language);
}
