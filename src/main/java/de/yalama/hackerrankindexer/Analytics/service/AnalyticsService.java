package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.models.UsageData;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public abstract class AnalyticsService {

    /**
     * A method to clear all cached statistics
     * Fired when Dataset is manipulated
     */
    public abstract void clear(User user);

    /**
     * A method to check if submissions exist
     */
    public abstract boolean checkSubmissionsExist(User user);

    /**
     * A method to get the linked GeneralPercentage instance linked to a user
     * @param User user
     * @return
     */
    public abstract GeneralPercentage getGeneralPercentages(User user);

    /**
     * A method to get the usage percentages related to a user
     * @param user the user
     * @return usage percentages of a user
     */
    public abstract Set<UsageData> getUsagePercentages(User user);

    /**
     * A method to get the pass percentages of a user
     * @param user the user
     * @return the pass percentages of a user
     */
    public abstract Set<PassPercentage> getPassPercentages(User user);

    public abstract Long getNumberOfUsers();

    public abstract Long getNumberOfSubmissions();
}
