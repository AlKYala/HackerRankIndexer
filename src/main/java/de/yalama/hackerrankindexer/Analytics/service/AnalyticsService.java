package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Analytics.UsagePercentages;
import de.yalama.hackerrankindexer.Challenge.Model.Challenge;

import java.util.List;
import java.util.Map;

public abstract class AnalyticsService {
    /**
     * @return The percentage of (passed submissions) / (all submissions)
     */
    public abstract Double getPercentagePassedSubmissions();

    /**
     *
     * @return The percentage of (passed challenges) / (all attempted challenges)
     */
    public abstract Double getPercentagePassedChallenges();

    /**
     * Returns the percentage of passed submissions by language
     * @param languageId the ID of the language in question
     * @return the percentage of passed submissions of language with ID languageId
     */
    public abstract Double getPercentagePassedByLanguage(Long languageId);

    /**
     * Returns the percentage of passed submissions per challenge
     * @param challengeId
     * @return the percentage of passed submissions per challenge with ID challengeId
     */
    public abstract Double getPercentagePassedByChallenge(Long challengeId);

    /**
     * From all submissions give the share of submissions of each language in percent
     * @return A Map with the percentage of submissions (total) for each language
     */
    public abstract UsagePercentages getUsagePercentages();

    /**
     * A method to clear all cached statistics
     * Fired when Dataset is manipulated
     */
    public abstract void clear();
}
