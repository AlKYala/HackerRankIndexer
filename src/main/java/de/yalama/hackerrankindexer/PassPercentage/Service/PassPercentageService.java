package de.yalama.hackerrankindexer.PassPercentage.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.User.Model.User;

public abstract class PassPercentageService {

    public abstract PassPercentage findById(long id);

    public abstract int createAll(User user);

    public abstract PassPercentage create(User user, PLanguage pLanguage);
}
