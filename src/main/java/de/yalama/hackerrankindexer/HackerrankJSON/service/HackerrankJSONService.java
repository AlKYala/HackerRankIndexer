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
import de.yalama.hackerrankindexer.UserData.Service.UserDataService;
import de.yalama.hackerrankindexer.shared.services.ColorPickerUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

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

    @Autowired
    private UserDataService userDataService;


    public Integer parse(HackerrankJSON hackerrankJSON, User user) {

        if(user.getUserData().size() > 2) {
            return 0;
        }

        UserData userData = new UserData();
        userData.setUser(user);
        userData = this.userDataService.save(userData);
        this.createSubmissionsFromData(hackerrankJSON.getSubmissions(), userData);

        this.userService.update(user.getId(), user);
        this.userDataService.update(userData.getId(), userData);
        return 1;
    }

    /**
     * Gets a pLanguage-Instnace from DB
     * If it does not exist, instantiate Instance, give it needed attributes and persist
     * @param pLanguageName the name to search by in pLanguage table
     * @return The found or persisted instance of DB
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
     * Gets a challenge-Instnace from DB
     * If it does not exist, instantiate Instance, give it needed attributes and persist
     * @param challengeName the name to search by in challenge table
     * @return The found or persisted instance of DB
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
     * Gets a contest-Instnace from DB
     * If it does not exist, instantiate Instance, give it needed attributes and persist
     * @param contestName the name to search by in contest table
     * @return The found or persisted instance of DB
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

    /**
     * Creates Submissions and persists them in DB from given SubmissionJSON instances
     * Assigns or creates challenges, pLanguages and contests not found in DB
     * After creating submissions, creates percentage data
     *
     * All Data assigned to userdata
     * @param submissionJSONS The Array of submissionJSON instances
     * @param userData The User data to link the results
     */
    private void createSubmissionsFromData(SubmissionJSON[] submissionJSONS, UserData userData) {

        //MAPS for faster access - so is not always called from DB
        Map<String, Challenge> seenChallenges   = new HashMap<String, Challenge>();
        Map<String, PLanguage> seenPlanguages   = new HashMap<String, PLanguage>();
        Map<String, Contest> seenContests       = new HashMap<String, Contest>();

        List<Submission> submissions = new ArrayList<Submission>();

        for(SubmissionJSON submissionJSON : submissionJSONS) {

            //Challenge
            String tempChallengeName    = submissionJSON.getChallenge();

            boolean isChallengeMapped   = seenChallenges.containsKey(tempChallengeName);

            Challenge tempChallenge = (isChallengeMapped)
                    ? seenChallenges.get(tempChallengeName)
                    : this.getChallengeFromDB(tempChallengeName);

            //PLanguage
            String tempPlanguageName     = submissionJSON.getLanguage();

            boolean isPlanguageMapped   = seenPlanguages.containsKey(tempPlanguageName);

            PLanguage tempPLanguage = (isPlanguageMapped)
                    ? seenPlanguages.get(tempPlanguageName)
                    : this.getPlanguageFromDB(tempPlanguageName);

            //Contest
            String tempContestName      = submissionJSON.getContest();

            boolean isContestMapped     = seenContests.containsKey(tempContestName);

            Contest tempContest     = (isContestMapped)
                    ? seenContests.get(tempContestName)
                    : this.getContestFromDB(submissionJSON.getContest());

            //Submission

            Submission submission =
                    this.createSubmissionFromJSON(submissionJSON, tempChallenge, tempPLanguage, tempContest, userData);

            userData.getUsedPLanguages().add(tempPLanguage);
            submission = this.submissionService.save(submission);
            submissions.add(submission);
        }
        this.createStatisticsData(userData, userData.getUsedPLanguages());
    }

    /**
     * Creates a Submission instance from a submissionJSON
     * All attributes from the sumbissionJson are used for attributes in the submission insntance
     * Challenges, PLanguages, Contests and the UserData instance are set for submission
     * @param json The Submssion JSON instance
     * @return a submission instance with all attributes filled
     */
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

    /**
     * Creates statistics based on passed UserData and the languages from userData (collected in createSubmissions)
     * Creates a PassPercentage, UsagePercentage and GeneralPercentage instance for UserData
     * @param userData      The userData to associate the Percentages to
     * @param languagesUsed The languages used in the submitted json
     */
    private void createStatisticsData(UserData userData,
                                      Collection<PLanguage> languagesUsed) {
        for(PLanguage pLanguage : languagesUsed) {
            UsagePercentage usagePercentage = this.usagePercentageService.create(userData, pLanguage);
            PassPercentage passPercentage = this.passPercentageService.create(userData, pLanguage);
            this.passPercentageService.save(passPercentage);
            this.usagePercentageService.save(usagePercentage);
        }
        this.generalPercentageService.calculatePercentageForUserData(userData);
        userData = this.userDataService.update(userData.getId(), userData);
    }
}
