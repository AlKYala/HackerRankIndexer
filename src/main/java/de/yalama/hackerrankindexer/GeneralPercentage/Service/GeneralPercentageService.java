package de.yalama.hackerrankindexer.GeneralPercentage.Service;

import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.GeneralPercentage.Repository.GeneralPercentageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.User.Model.User;

public abstract class GeneralPercentageService {

    /**
     * Calculates a users general percentages.
     * @param user
     */
    public abstract void calculateUsersGeneralPercentages(User user);
}
