package de.yalama.hackerrankindexer.HackerrankJSON.service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.Contest.Model.Contest;
import de.yalama.hackerrankindexer.Contest.Service.ContestService;
import de.yalama.hackerrankindexer.HackerrankJSON.model.HackerrankJSON;
import de.yalama.hackerrankindexer.HackerrankJSON.model.SubmissionJSON;
import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@NoArgsConstructor
public class HackerrankJSONService {

    /**
     * parses the received instance (JSON parsed in frontend!) and persists data for it
     * @param hackerrankJSON
     */

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private PLanguageService pLanguageService;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private ContestService contestService;

    private Map<String, PLanguage> foundPLanguages;

    private Map<String, Challenge> foundChallenges;

    private Map<String, Contest> foundContests;

    private boolean mapsCreated = false;


    public Integer parse(HackerrankJSON hackerrankJSON, long sessionId) {
        //debug
        System.out.println("start");
        if(!mapsCreated) {
            this.foundChallenges = new HashMap<String, Challenge>();
            this.foundContests = new HashMap<String, Contest>();
            this.foundPLanguages = new HashMap<String, PLanguage>();
        }
        this.createMapData(hackerrankJSON.getSubmissions(), foundPLanguages, foundChallenges, foundContests);
        this.gatherInfoFromSubmissions(hackerrankJSON.getSubmissions(), foundPLanguages, foundChallenges, foundContests);
        this.createSubmissionsFromData(hackerrankJSON.getSubmissions(), foundChallenges, foundPLanguages, foundContests,
                sessionId);
        //log.info("Parsing complete");
        return 1;
    }

    private void gatherInfoFromSubmissions(SubmissionJSON[] submissionJSONS, Map<String, PLanguage> pLanguageMap,
                                           Map<String, Challenge> challengeMap, Map<String, Contest> contestMap) {
        this.createMapData(submissionJSONS, pLanguageMap, challengeMap, contestMap);

    }

    private void createMapData(SubmissionJSON[] submissionJSONS, Map<String, PLanguage> pLanguageMap,
                               Map<String, Challenge> challengeMap, Map<String, Contest> contestMap) {
        for(SubmissionJSON submission: submissionJSONS) {
            this.addLanguageIfNeeded(submission.getLanguage(), pLanguageMap);
            this.addChallengeIfNeeded(submission.getChallenge(), challengeMap);
            this.addContestIfNeeded(submission.getContest(), contestMap);
        }
    }

    //TODO ueberarbeiten - irgendwie ist die Map nicht ok so
    private void addLanguageIfNeeded(String language) {
        if(!pLanguageMap.containsKey(language)) {
            PLanguage found = new PLanguage();
            found.setId(0L);
            found.setLanguage(language);
            found.setSubmissions(Collections.emptySet());
            found = this.pLanguageService.persist(found);
            pLanguageMap.put(language, found);
        }
    }
    //TODO moeglicherweise ueberarbeiten
    private void addChallengeIfNeeded(String challenge, Map<String, Challenge> challengeMap) {
        if(!challengeMap.containsKey(challenge)) {
            Challenge found = new Challenge();
            found.setChallengeName(challenge);
            found.setId(0L);
            found.setSubmissions(Collections.emptySet());
            found = this.challengeService.persist(found);
            challengeMap.put(challenge, found);
        }
    }
    //TODO moeglicherweise ueberarbeiten
    private void addContestIfNeeded(String contest, Map<String, Contest> contestMap) {
        if(!contestMap.containsKey(contest)) {
            Contest found = new Contest();
            found.setName(contest);
            found.setId(0L);
            found.setSubmissions(Collections.emptySet());
            found = this.contestService.save(found);
            contestMap.put(contest, found);
        }
    }

    private void createSubmissionsFromData(SubmissionJSON[] submissionJSONS, Map<String, Challenge> challengeMap,
                                           Map<String, PLanguage> pLanguageMap, Map<String, Contest> contestMap,
                                           long sessionId) {
        Submission[] submissions = new Submission[submissionJSONS.length];
        for(int i = 0; i < submissions.length; i++) {
            Submission submission =
                    this.createSubmissionFromJSON(submissionJSONS[i], challengeMap, pLanguageMap, contestMap, sessionId);
            this.submissionService.save(submission);
        }
    }

    private Submission createSubmissionFromJSON(SubmissionJSON json, Map<String, Challenge> challengeMap,
                                                Map<String, PLanguage> pLanguageMap, Map<String, Contest> contestMap,
                                                long sessionId) {
        Submission submission = new Submission();
        submission.setId(0L);
        submission.setCode(json.getCode());
        submission.setScore(json.getScore());
        submission.setLanguage(pLanguageMap.get(json.getLanguage()));
        submission.setChallenge(challengeMap.get(json.getChallenge()));
        submission.setContest(contestMap.get(json.getContest()));
        submission.setSessionId(sessionId);
        return submission;
    }
}
