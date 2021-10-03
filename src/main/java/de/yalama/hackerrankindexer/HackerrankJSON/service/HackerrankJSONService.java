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
import de.yalama.hackerrankindexer.shared.services.DummyService;
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

    @Autowired
    private DummyService dummyService;

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
        this.createMapData(hackerrankJSON.getSubmissions());
        this.gatherInfoFromSubmissions(hackerrankJSON.getSubmissions());
        this.createSubmissionsFromData(hackerrankJSON.getSubmissions(),
                sessionId);
        //log.info("Parsing complete");
        return 1;
    }

    private void gatherInfoFromSubmissions(SubmissionJSON[] submissionJSONS) {
        this.createMapData(submissionJSONS);

    }

    private void createMapData(SubmissionJSON[] submissionJSONS) {
        for(SubmissionJSON submission: submissionJSONS) {
            this.addLanguageIfNeeded(submission.getLanguage());
            this.addChallengeIfNeeded(submission.getChallenge());
            this.addContestIfNeeded(submission.getContest());
        }
    }

    private void addLanguageIfNeeded(String language) {
        if(!this.foundPLanguages.containsKey(language)) {
            PLanguage dummy = this.dummyService.getDummyPLanguage(language);
            this.foundPLanguages.put(language, this.pLanguageService.persist(dummy));
        }
    }

    private void addChallengeIfNeeded(String challenge) {
        if(!this.foundChallenges.containsKey(challenge)) {
            Challenge dummy = this.dummyService.getDummyChallenge(challenge);
            this.foundChallenges.put(challenge, this.challengeService.persist(dummy));
        }
    }

    private void addContestIfNeeded(String contest) {
        if(!this.foundContests.containsKey(contest)) {
            Contest dummy = this.dummyService.getDummyContest(contest);
            this.foundContests.put(contest, this.contestService.persist(dummy));
        }
    }

    private void createSubmissionsFromData(SubmissionJSON[] submissionJSONS, long sessionId) {
        Submission[] submissions = new Submission[submissionJSONS.length];
        for(int i = 0; i < submissions.length; i++) {
            Submission submission =
                    this.createSubmissionFromJSON(submissionJSONS[i], sessionId);
            this.submissionService.save(submission);
        }
    }

    private Submission createSubmissionFromJSON(SubmissionJSON json, long sessionId) {
        Submission submission = new Submission();
        submission.setId(0L);
        submission.setCode(json.getCode());
        submission.setScore(json.getScore());
        submission.setLanguage(this.foundPLanguages.get(json.getLanguage()));
        submission.setChallenge(this.foundChallenges.get(json.getChallenge()));
        submission.setContest(this.foundContests.get(json.getContest()));
        submission.setSessionId(sessionId);
        return submission;
    }
}
