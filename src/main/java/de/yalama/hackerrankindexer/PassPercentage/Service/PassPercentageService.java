package de.yalama.hackerrankindexer.PassPercentage.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;

public abstract class PassPercentageService {

    public abstract PassPercentage findById(long id);

    public abstract int createAll(UserData userData);

    //TODO
    public abstract PassPercentage create(UserData userData, PLanguage pLanguage);

    //TODO
    public abstract PassPercentage findByUserAndLanguage(UserData userData, PLanguage pLanguage);
}
