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
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private UserService userService;

    public void parse(HackerrankJSON hackerrankJSON) {
        Map<String, PLanguage> foundPLanguages = new HashMap<String, PLanguage>();
        Map<String, Challenge> foundChallenges = new HashMap<String, Challenge>();
        Map<String, Contest> foundContests = new HashMap<String, Contest>();
        this.createMapData(hackerrankJSON.getSubmissions(), foundPLanguages, foundChallenges, foundContests);
        User user = this.persistUser(hackerrankJSON.getEmail(), hackerrankJSON.getUsername());
        this.gatherInfoFromSubmissions(hackerrankJSON.getSubmissions(), foundPLanguages, foundChallenges, foundContests);
        this.createSubmissionsFromData(hackerrankJSON.getSubmissions(), foundChallenges, foundPLanguages, foundContests,
                user);
    }

    private User persistUser(String email, String username) {
        User user = new User();
        user.setId(0L);
        user.setSubmittedEntries(Collections.EMPTY_SET);
        user.setUsername(username);
        user.setEmail(email);
        return this.userService.save(user);
    }

    private void gatherInfoFromSubmissions(SubmissionJSON[] submissionJSONS, Map<String, PLanguage> pLanguageMap,
                                           Map<String, Challenge> challengeMap, Map<String, Contest> contestMap) {
        this.createMapData(submissionJSONS, pLanguageMap, challengeMap, contestMap);

    }

    private void createMapData(SubmissionJSON[] submissionJSONS, Map<String, PLanguage> pLanguageMap,
                               Map<String, Challenge> challengeMap, Map<String, Contest> contestMap) {
        for(SubmissionJSON submission: submissionJSONS) {
            this.addLanguageIfNeeded(submission.getLanguage(), pLanguageMap);
            this.addChallengeIfNeeded(submission.getLanguage(), challengeMap);
            this.addContestIfNeeded(submission.getContest(), contestMap);
        }
    }

    private void addLanguageIfNeeded(String language, Map<String, PLanguage> pLanguageMap) {
        if(!pLanguageMap.containsKey(language)) {
            PLanguage found = new PLanguage();
            found.setId(0L);
            found.setLanguage(language);
            found.setSubmissions(Collections.emptySet());
            found = this.pLanguageService.save(found);
            pLanguageMap.put(language, found);
        }
    }

    private void addChallengeIfNeeded(String challenge, Map<String, Challenge> challengeMap) {
        if(!challengeMap.containsKey(challenge)) {
            Challenge found = new Challenge();
            found.setChallengeName(challenge);
            found.setId(0L);
            found.setSubmissions(Collections.emptySet());
            found = this.challengeService.save(found);
            challengeMap.put(challenge, found);
        }
    }

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
                                                   User user) {
        Submission[] submissions = new Submission[submissionJSONS.length];
        for(int i = 0; i < submissions.length; i++) {
            Submission submission =
                    this.createSubmissionFromJSON(submissionJSONS[i], challengeMap, pLanguageMap, contestMap, user);
            this.submissionService.save(submission);
        }
    }

    private Submission createSubmissionFromJSON(SubmissionJSON json, Map<String, Challenge> challengeMap,
                                                Map<String, PLanguage> pLanguageMap, Map<String, Contest> contestMap,
                                                User user) {
        Submission submission = new Submission();
        submission.setId(0L);
        submission.setCode(json.getCode());
        submission.setScore(json.getScore());
        submission.setWriter(user);
        submission.setLanguage(pLanguageMap.get(json.getLanguage()));
        submission.setChallenge(challengeMap.get(json.getChallenge()));
        submission.setContest(contestMap.get(json.getContest()));
        return submission;
    }
}
