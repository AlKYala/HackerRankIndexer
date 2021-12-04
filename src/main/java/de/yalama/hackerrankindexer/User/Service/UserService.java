package de.yalama.hackerrankindexer.User.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.services.BaseService;

public abstract class UserService implements BaseService<User> {

    public abstract PLanguage getFavouriteLanguage();

    public abstract double getGeneralSubmissionPassPercentage();

    public abstract double getGeneralChallengePassPercentage();
}