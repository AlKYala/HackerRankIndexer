package de.yalama.hackerrankindexer.UsagePercentage.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;

import java.util.List;

public abstract class UsagePercentageService {

    public abstract UsagePercentage create(UserData userData, PLanguage pLanguage);

    public abstract int createAll(UserData userData);

    public abstract List<UsagePercentage> findAll();
}
