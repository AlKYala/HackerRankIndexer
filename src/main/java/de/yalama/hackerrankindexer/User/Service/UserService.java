package de.yalama.hackerrankindexer.User.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import java.util.Set;

public abstract class UserService implements BaseService<User> {

    public abstract PLanguage getFavouriteLanguage(User user);

    public abstract double getGeneralSubmissionPassPercentage(User user);

    public abstract double getGeneralChallengePassPercentage(User user);

    public abstract User findByUsername(String username);

    public abstract Set<Submission> findSubmissionsOfUserOfLanguage(User user, PLanguage language);
}
