package de.yalama.hackerrankindexer.UsagePercentage.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.User.Model.User;

import java.util.List;

public abstract class UsagePercentageService {

    public abstract UsagePercentage create(User user, PLanguage pLanguage);

    public abstract int createAll(User user);

    public abstract List<UsagePercentage> findAll();
}
