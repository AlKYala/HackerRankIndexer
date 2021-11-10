package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.Analytics.SupportModels.ChartEntry;
import de.yalama.hackerrankindexer.Analytics.SupportModels.PassPercentageChartData;
import de.yalama.hackerrankindexer.Analytics.SupportModels.UsageStatistics;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.shared.models.PassData;
import de.yalama.hackerrankindexer.shared.services.ColorPickerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Slf4j
public class AnalyticsServiceImpl extends AnalyticsService {

    private SubmissionService submissionService;
    private ChallengeService challengeService;
    private PLanguageService pLanguageService;

    private Map<String, Double> percentageSubmissionsBySessionId;

    private Map<String, Double> percentageChallengesbySessionId;

    private Map<String, Map<Long, Double>> percentageByLanguageIdBySessionId; //SessionId is outer dimension

    private Map<String, Map<Long, Double>> percentageByChallengeIdBySessionId;

    private Map<String, UsageStatistics> usageStatisticsBySessionId;

    /**
     * sessionId : Map<LanguageId, Passdata>
     */
    private Map<String, Map<Long, PassData>> passDataInstances;

    public AnalyticsServiceImpl(SubmissionService submissionService, ChallengeService challengeService,
                                PLanguageService pLanguageService) {
        this.pLanguageService = pLanguageService;
        this.submissionService = submissionService;
        this.challengeService = challengeService;
        this.percentageSubmissionsBySessionId = new HashMap<String, Double>();
        this.percentageChallengesbySessionId = new HashMap<String, Double>();
        this.percentageByLanguageIdBySessionId = new HashMap<String, Map<Long, Double>>();
        this.percentageByChallengeIdBySessionId = new HashMap<String, Map<Long, Double>>();
        this.usageStatisticsBySessionId = new HashMap<String, UsageStatistics>();
        this.passDataInstances = new HashMap<String, Map<Long, PassData>>();
    }

    //INITIALIZATION RELATED

    //TODO remodel parameter
    @Override
    public boolean checkSubmissionsExistBySessionId(String sessionId) {

        log.info("Checking sessionId for: {}", sessionId);

        return this.submissionService.findAllBySessionId(sessionId).size() > 0;
    }

    @Override
    public void clearEverythingBySessionId(String sessionId) {
        this.percentageSubmissionsBySessionId.remove(sessionId);
        this.percentageChallengesbySessionId.remove(sessionId);
        this.percentageByLanguageIdBySessionId.remove(sessionId);
        this.percentageByChallengeIdBySessionId.remove(sessionId);
        this.usageStatisticsBySessionId.remove(sessionId);
    }

    //GENERAL STATS RELATED

    @Override
    public Double getPercentagePassedSubmissions(String sessionId) {
        if (!percentageSubmissionsBySessionId.containsKey(sessionId)) {
            int passed = this.submissionService.getAllPassed(sessionId).size();
            int total = this.submissionService.findAllBySessionId(sessionId).size();
            double percentageForSessionId = ((double) passed) / ((double) total);
            this.percentageSubmissionsBySessionId.put(sessionId, percentageForSessionId);
        }
        return this.percentageSubmissionsBySessionId.get(sessionId);
    }

    @Override
    public Double getPercentagePassedChallenges(String  sessionId) {
        if (!percentageChallengesbySessionId.containsKey(sessionId)) {
            int passed = this.challengeService.getAllPassedChallengesBySessionId(sessionId).size();
            int total = this.challengeService.getAllChallengesBySessionId(sessionId).size();
            double percentageChallengesForSessionId = ((double) passed) / ((double) total);
            this.percentageChallengesbySessionId.put(sessionId, percentageChallengesForSessionId);
        }
        return this.percentageChallengesbySessionId.get(sessionId);
    }

    //LANGUAGE SPECIFIC STATS

    @Override
    public Double getPercentagePassedByLanguage(Long languageId, String sessionId) {
        if (!this.checkLanguagePercentageForSessionIdExists(sessionId, languageId)) {

            List<Submission> submissionsBySessionIdWithLanguage =
                    this.pLanguageService.findSubmissionsOfLanguageAndSessionId(languageId, sessionId);

            int total = submissionsBySessionIdWithLanguage.size();
            int[] passed = new int[1];

            submissionsBySessionIdWithLanguage.forEach(submission -> passed[0] += (int) submission.getScore());

            this.addPercentage(languageId, sessionId, passed[0], total, this.percentageByLanguageIdBySessionId);
        }
        return this.percentageByLanguageIdBySessionId.get(sessionId).get(languageId);
    }

    private boolean checkLanguagePercentageForSessionIdExists(String sessionId, long languageId) {
        return this.percentageByLanguageIdBySessionId.containsKey(sessionId) &&
                this.percentageByLanguageIdBySessionId.get(sessionId).containsKey(languageId);
    }

    private void addPercentage(long id, String sessionId, int passed, int total, Map<String, Map<Long, Double>> map) {
        double percentage = ((double) passed) / ((double) total);
        percentage = this.roundToDecimal(percentage, 2);
        map.get(sessionId).put(id, percentage);
    }

    private Double roundToDecimal(double value, int places) {
        BigDecimal rounder = BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP);
        return rounder.doubleValue();
    }

    // PASS DATA RELATED / CHARTS RELATED
    @Override
    public Collection<PassData> getPassDataForAllLanguages(String sessionId) {
        List<PLanguage> pLanguagesOfUser = this.pLanguageService.findPLanguagesUsedBySessionId(sessionId);
        List<PassData> passData = new ArrayList<PassData>();
        for(PLanguage pLanguage : pLanguagesOfUser) {
            passData.add(this.getPassDataForLangauge(pLanguage.getId(), sessionId));
        }
        return passData;
    }

    @Override
    public PassData getPassDataForLangauge(Long id, String sessionId) {
        return (this.passDataInstances.containsKey(sessionId) && this.passDataInstances.get(sessionId).containsKey(id)) ?
                this.passDataInstances.get(sessionId).get(id) : this.generatePassDataForLanguageAndPersist(id, sessionId);
    }

    private PassData generatePassDataForLanguageAndPersist(Long id, String sessionId) {
        if(!this.passDataInstances.containsKey(sessionId)) {
            this.passDataInstances.put(sessionId, new HashMap<Long, PassData>());
        }

        PassData passData = new PassData();
        PLanguage language = this.pLanguageService.findById(id);
        Integer passed = this.pLanguageService.getPassedSubmissionsForLanguage(id, sessionId).size();
        Integer failed = this.pLanguageService.getFailedSubmissionsForLanguage(id, sessionId).size();
        passData.setFailed(failed);
        passData.setPassed(passed);
        passData.setLanguageId(language.getId());
        passData.setLanguageName(language.getLanguage());

        this.passDataInstances.get(sessionId).put(id, passData);

        return passData;
    }

    @Override
    public Collection<PassPercentageChartData> getPassPercentageChartData(String sessionId) {
        Collection<PassData> passDataForAllLanguages = this.getPassDataForAllLanguages(sessionId);
        Collection<PassPercentageChartData> chartData = new ArrayList<PassPercentageChartData>();
        for(PassData passData: passDataForAllLanguages) {
            chartData.add(PassPercentageChartData.generateFromPassData(passData));
        }
        return chartData;
    }


    @Override
    public Collection<ChartEntry> getUsageChartEntries(String sessionId) {
        UsageStatistics usageStatistics = this.getUsagePercentagesBySessionId(sessionId);
        return this.getChartEntriesFromUsageStatistics(usageStatistics);
    }

    private Collection<ChartEntry> getChartEntriesFromUsageStatistics(UsageStatistics usageStatistics) {
        List<ChartEntry> entries = new ArrayList<ChartEntry>();
        for(int i = 0; i < usageStatistics.size(); i++) {
            String name = usageStatistics.getPLanguages().get(i).getLanguage();
            int numSubmissions = usageStatistics.getNumberSubmissions().get(i);
            entries.add(new ChartEntry(name, numSubmissions));
        }
        return entries;
    }

    @Override
    public String[] getAnalyticsColors() {
        return ColorPickerUtil.getColors();
    }

    // PASS DATA END
    //TODO bis hierhin ok

    //USAGE STATISTICS RELATED

    @Override
    public UsageStatistics getUsagePercentagesBySessionId(String sessionId) {
        if (!this.usageStatisticsBySessionId.containsKey(sessionId)) {
            this.createUsagePercentages(sessionId);
        }
        return this.usageStatisticsBySessionId.get(sessionId);
    }

    private void createUsagePercentages(String sessionId) {
        if(this.usageStatisticsBySessionId.containsKey(sessionId)) {
            System.out.println("ALREADY EXISTS");
            return;
        }
        UsageStatistics usageStatisticsForId = new UsageStatistics();
        this.usageStatisticsBySessionId.put(sessionId, usageStatisticsForId);

        this.pLanguageService
                .findPLanguagesUsedBySessionId(sessionId)
                .forEach(pLanguage -> this.addPLanguageToUsageStatistics(pLanguage, sessionId));
        usageStatisticsForId.setCreated(true);

    }

    private void addPLanguageToUsageStatistics(PLanguage pLanguage, String sessionId) {
        UsageStatistics reference = this.usageStatisticsBySessionId.get(sessionId);
        int numSubmission = (int) pLanguage.getSubmissions()
                .stream()
                .filter(submission -> submission.getSessionId().equals(sessionId)).count();
        reference.getPLanguages().add(pLanguage);
        reference.getNumberSubmissions().add(numSubmission);
    }
}
