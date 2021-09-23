package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;

public abstract class AnalyticsService {
    /**
     * @return The percentage of (passed submissions) / (all submissions)
     */
    public abstract Double getPercentagePassedSubmissions(long sessionId);

    /**
     *
     * @return The percentage of (passed challenges) / (all attempted challenges)
     */
    public abstract Double getPercentagePassedChallenges(long sessionId);

    /**
     * Returns the percentage of passed submissions by language
     * @param languageId the ID of the language in question
     * @return the percentage of passed submissions of language with ID languageId
     */
    public abstract Double getPercentagePassedByLanguage(Long languageId, long sessionId);

    /**
     * Returns the percentage of passed submissions per challenge
     * @param challengeId
     * @return the percentage of passed submissions per challenge with ID challengeId
     */
    public abstract Double getPercentagePassedByChallenge(Long challengeId, long SessionId);

    /**
     * From all submissions give the share of submissions of each language in percent
     * @return two arrays with the percentage of submissions (total) for each language (Indices match)
     */
    public abstract UsageStatistics getUsagePercentages(long sessionId);

    /**
     * From all submissions give the suuccess rate of submissions of each language in percent
     * @return two arrays with the percentage of submissions (total) for each language (Indices match)
     */
    public abstract PassPercentages getPassPercentages(long sessionId);

    /**
     * returns the most used Language
     * @return
     */
    public abstract PLanguage getFavouriteLanguage(long sessionId);

    /**
     * A method to clear all cached statistics
     * Fired when Dataset is manipulated
     */
    public abstract void clear(long sessionId);

    /**
     * A method to check if submissions exist
     */
    public abstract boolean checkSubmissionsExist(long sessionId);
}
