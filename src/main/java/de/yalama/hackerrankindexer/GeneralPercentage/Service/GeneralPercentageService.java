package de.yalama.hackerrankindexer.GeneralPercentage.Service;

import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.GeneralPercentage.Repository.GeneralPercentageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.services.BaseService;

public abstract class GeneralPercentageService implements BaseService<GeneralPercentage> {

    /**
     * Calculates the general Percentage for a user Data instance, based on
     * the submissions found for userData
     * @param UserData
     */
    public abstract void calculatePercentageForUserData(UserData userData);

    /**
     * A method to find the favourite Language for a userData instance
     * Called by GeneralPercentageService::calculatePercentageForUserData
     * and is mandatory for every implementing service
     * For most submissions in a language
     * @param userData The userData instance in question
     * @return The favourite language - PLanguage instance
     */
    public abstract PLanguage findFavouriteLanguageForUserData(UserData userData);

    /**
     * Calculates the percentage of passed submissions.
     * The idea is: passed / all submissions
     * Integer for flooring reasons
     * @param userData
     * @return The percentage of passed submissions
     */
    public abstract Integer getSubmissionsPassedPercentage(UserData userData);

    /**
     * Calculates the percentage of passed challenges
     * The idea is:
     * n := the number of attempted challenges
     * m := the number of challenges with at least one passed challenge
     *
     * The result is m/n
     * @param userData The user Data in question
     * @return The percentage of passed challenges - integer for flooring reasons
     */
    public abstract Integer getChallengesPassedPercentage(UserData userData);
}
