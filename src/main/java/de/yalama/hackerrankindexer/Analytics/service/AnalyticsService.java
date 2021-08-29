package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;

import java.util.List;

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
     * A method to clear all cached statistics
     * Fired when Dataset is manipulated
     */
    public abstract void clear();
}
