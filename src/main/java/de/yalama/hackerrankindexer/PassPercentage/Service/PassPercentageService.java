package de.yalama.hackerrankindexer.PassPercentage.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;

public abstract class PassPercentageService {

    public abstract PassPercentage findById(long id);

    public abstract int createAll(UserData userData);

    public abstract PassPercentage create(UserData userData, PLanguage pLanguage);

    public abstract PassPercentage findByUserAndLanguage(User user, PLanguage pLanguage);

    public abstract boolean existsByUserAndPLanguage(User user, PLanguage pLanguage);
}
