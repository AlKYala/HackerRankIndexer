package de.yalama.hackerrankindexer.HackerrankJSON.service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.Contest.Model.Contest;
import de.yalama.hackerrankindexer.Contest.Service.ContestService;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.GeneralPercentage.Service.GeneralPercentageService;
import de.yalama.hackerrankindexer.HackerrankJSON.model.HackerrankJSON;
import de.yalama.hackerrankindexer.HackerrankJSON.model.SubmissionJSON;
import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageInfoService;
import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.PassPercentage.Service.PassPercentageService;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.UsagePercentage.Service.UsagePercentageService;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.services.ColorPickerUtil;
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
    private GeneralPercentageService generalPercentageService;

    @Autowired
    private UsagePercentageService usagePercentageService;

    @Autowired
    private UserService userService;

    @Autowired
    private PassPercentageService passPercentageService;

    @Autowired
    private PLanguageInfoService pLanguageInfoService;

    //TODO: Return UserData?
    public Integer parse(HackerrankJSON hackerrankJSON, User user) {

        if

        this.createSubmissionsFromData(hackerrankJSON.getSubmissions(), user);
        this.userService.update(user.getId(), user);
        return 1;
    }

    /**
     * TODO
     * @param language
     * @return
     */
    private PLanguage getPlanguageFromDB(String pLanguageName) {
        PLanguage pLanguage = this.pLanguageService.findByName(pLanguageName);

        if(pLanguage != null)
            return pLanguage;

        pLanguage = new PLanguage();
        pLanguage.setLanguage(pLanguageName);
        String fileExtension = this.pLanguageInfoService.getFileExtension(pLanguage);
        String displayName = this.pLanguageInfoService.getPLanguageDisplayName(pLanguageName);
        pLanguage.setColor(ColorPickerUtil.getNextColor());
        pLanguage.setDisplayName(displayName);
        pLanguage.setFileExtension(fileExtension);

        return this.pLanguageService.save(pLanguage);
    }

    /**
     * TODO
     * @param challengeName
     * @return
     */
    private Challenge getChallengeFromDB(String challengeName) {

        Challenge challenge = this.challengeService.findByChallengeName(challengeName);

        if(challenge != null) {
            return challenge;
        }

        challenge = new Challenge();
        challenge.setChallengeName(challengeName);
        return this.challengeService.save(challenge);
    }

    /**
     * TODO
     * @param contestName
     * @return
     */
    private Contest getContestFromDB(String contestName) {
        Contest contest = this.contestService.findByName(contestName);

        if(contest != null) {
            return contest;
        }

        contest = new Contest();
        contest.setName(contestName);

        return this.contestService.save(contest);
    }

    private void createSubmissionsFromData(SubmissionJSON[] submissionJSONS, UserData userData) {

        UsagePercentage usagePercentageForUser  = new UsagePercentage();
        PassPercentage passPercentage           = new PassPercentage();
        GeneralPercentage generalPercentage     = new GeneralPercentage();

        //CollectionsToHelp
        Map<String, Integer> languageNamesMappedToTimesUsed = new HashMap<String, Integer>();
        double[] passedVsAll = new double[2];

        //MAPS for faster access - so is not always called from DB
        Map<String, Challenge> seenChallenges   = new HashMap<String, Challenge>();
        Map<String, PLanguage> seenPlanguages   = new HashMap<String, PLanguage>();
        Map<String, Contest> seenContests       = new HashMap<String, Contest>();

        for(SubmissionJSON submissionJSON : submissionJSONS) {

            String tempChallengeName    = submissionJSON.getChallenge();
            String tempPlanguageName     = submissionJSON.getLanguage();
            String tempContestName      = submissionJSON.getContest();

            boolean isChallengeMapped   = seenChallenges.containsKey(tempChallengeName);
            boolean isPlanguageMapped   = seenPlanguages.containsKey(tempPlanguageName);
            boolean isContestMapped     = seenContests.containsKey(tempContestName);

            Challenge tempChallenge = (isChallengeMapped)
                    ? seenChallenges.get(tempChallengeName)
                    : this.getChallengeFromDB(tempChallengeName);


            PLanguage tempPLanguage = (isPlanguageMapped)
                    ? seenPlanguages.get(tempPlanguageName)
                    : this.getPlanguageFromDB(tempPlanguageName);


            Contest tempContest     = (isContestMapped)
                    ? seenContests.get(tempContestName)
                    : this.getContestFromDB(submissionJSON.getContest());

            //for challenge, language, Plangage - if not exists - add to map

            Submission submission =
                    this.createSubmissionFromJSON(submissionJSON, tempChallenge, tempPLanguage, tempContest, userData);

            userData.getUsedPLanguages().add(tempPLanguage);
            userData.getSubmissionList().add(submission);

            this.submissionService.save(submission);
        }

        //setUsagePercentage
        //setGeneralPercentage
        //setPassPercentage
    }

    private Submission createSubmissionFromJSON(SubmissionJSON json, Challenge challenge,
                                                PLanguage pLanguage, Contest contest,
                                                UserData userData) {
        Submission submission = new Submission();
        submission.setId(0L);
        submission.setCode(json.getCode());
        submission.setScore(json.getScore());
        submission.setLanguage(pLanguage);
        submission.setChallenge(challenge);
        submission.setContest(contest);

        submission.setUserData(userData);
        return submission;
    }
}
