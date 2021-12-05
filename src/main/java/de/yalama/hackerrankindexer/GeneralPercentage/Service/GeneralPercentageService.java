package de.yalama.hackerrankindexer.GeneralPercentage.Service;

import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.GeneralPercentage.Repository.GeneralPercentageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.User.Model.User;

public abstract class GeneralPercentageService {

    public abstract GeneralPercentage findByUser(User user);

    public abstract GeneralPercentage create(User user);

    public abstract Double calculateSubmissionPercentage(User user);

    public abstract Double calculateChallengePercentage(User user);
}
