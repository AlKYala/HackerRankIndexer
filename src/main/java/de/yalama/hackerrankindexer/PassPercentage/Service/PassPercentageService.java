package de.yalama.hackerrankindexer.PassPercentage.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;

public abstract class PassPercentageService {

    /**
     * Creates all PassPercentages for found languages in the UserDataInstance
     * Calls PassPercentageSerivce::create
     * @param userData The UserData Instance to create PassPercentage for
     * @return An integer: 1 if sucess, else 0
     */
    public abstract int createAll(UserData userData);

    /**
     * Creates a pass Percentage instance and links it with PLanguage and UserData
     * @param userData The userdata instance to link the Passpercentage with
     * @param pLanguage The PLanguage the Passpercentage is for
     * @return A created PassPercentage instance
     */
    public abstract PassPercentage create(UserData userData, PLanguage pLanguage);

}
