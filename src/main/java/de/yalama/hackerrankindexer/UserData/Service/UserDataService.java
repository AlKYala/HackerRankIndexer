package de.yalama.hackerrankindexer.UserData.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.SubmissionFlat.Model.SubmissionFlat;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.UserData.Model.UserDataFlat;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import java.util.Collection;
import java.util.List;

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
    public abstract Collection<SubmissionFlat> findSubmissionsOfUserOfPlanguage(UserData userData, PLanguage language);

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

    /**
     * Takes the ID of a userData instance and generates a QR-Code for it
     * @param userDataId
     * @return
     */
    public abstract UserData generateQRCode(Long userDataId);
}
