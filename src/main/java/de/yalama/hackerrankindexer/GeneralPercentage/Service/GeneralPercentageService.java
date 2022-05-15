package de.yalama.hackerrankindexer.GeneralPercentage.Service;

import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.GeneralPercentage.Repository.GeneralPercentageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;

public abstract class GeneralPercentageService {

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

    /*

    SELECT p.*
    from planguage p
    where p.id in (
    select x.id
    from (
    SELECT p.id, count(s.id)
    FROM user_data ud
    inner join submission s on s.user_data_id = ud.id
    inner join planguage p on p.id = s.language_id
    where ud.id in (ud_id)
    group by p.id
    limit 1) x
    );
 */
    public abstract PLanguage getMostUsedLanguage(Long userDataId);
}
