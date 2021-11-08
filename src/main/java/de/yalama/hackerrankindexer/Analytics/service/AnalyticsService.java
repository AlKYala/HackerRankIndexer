package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentageChartData;
import de.yalama.hackerrankindexer.Analytics.SupportModels.ChartEntry;
import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentages;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.shared.models.PassData;

import java.util.Collection;

public abstract class AnalyticsService {
    /**
     * @return The percentage of (passed submissions) / (all submissions)
     */
    public abstract Double getPercentagePassedSubmissions(String sessionId);

    /**
     *
     * @return The percentage of (passed challenges) / (all attempted challenges)
     */
    public abstract Double getPercentagePassedChallenges(String sessionId);

    /**
     * Returns the percentage of passed submissions by language
     * @param languageId the ID of the language in question
     * @return the percentage of passed submissions of language with ID languageId
     */
    public abstract Double getPercentagePassedByLanguage(Long languageId, String sessionId);

    /**
     * From all submissions give the share of submissions of each language in percent
     * @return two arrays with the percentage of submissions (total) for each language (Indices match)
     */
    public abstract UsageStatistics getUsagePercentagesBySessionId(String sessionId);

    /**
     * From all submissions give the suuccess rate of submissions of each language in percent
     * @return two arrays with the percentage of submissions (total) for each language (Indices match)
     */
    public abstract PassPercentages getPassPercentages(String sessionId);

    /**
     * returns the most used Language
     * @return
     */
    public abstract PLanguage getFavouriteLanguage(String sessionId);

    /**
     * A method to check if submissions exist by SessionId
     */
    public abstract boolean checkSubmissionsExistBySessionId(String sessionId);

    public abstract void clearEverythingBySessionId(String sessionId);

    /**
     * Generates Pass Data for a language
     * @param id Id of the language
     * @param sessionId The
     * @return A pass data instance
     */
    public abstract PassData getPassDataForLangauge(Long id, String sessionId);

    /**
     * Returns a collection of passData of All Languages submitted by User
     * @param sessionId SessionId by User
     * @return a collection of passData of All Languages submitted by User
     */
    public abstract Collection<PassData> getPassDataForAllLanguages(String sessionId);

    /**
     * Essentially AnalyticsService::getPassDataForAllLanguages but new data types are used
     * to have data for ngx-charts, see here
     * https://swimlane.gitbook.io/ngx-charts/examples/bar-charts/normalized-horizontal-bar-chart
     * @param sessionId sessionId by user
     * @return
     */
    public abstract Collection<PassPercentageChartData> getPassPercentageChartData(String sessionId);

    /**
     * Returns a collection ChartEntry entities - used for usage Distribution in Frontend
     * @param sessionId sessionId by User
     * @return see description
     */
    public abstract Collection<ChartEntry> getUsageChartEntries(String sessionId);

    /**
     * @return an array of colors to be used in charts context
     */
    public abstract String[] getAnalyticsColors();
}
