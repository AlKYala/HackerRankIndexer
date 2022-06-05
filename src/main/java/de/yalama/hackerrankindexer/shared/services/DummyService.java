package de.yalama.hackerrankindexer.shared.services;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Contest.Model.Contest;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class DummyService {

    public Submission getDummySubmission() {
        /*Submission submission = new Submission();
        submission.setCode("dummycode");
        submission.setId(0L);
        submission.setScore(0.0);
        return submission;*/
        return null;
    }

    public Challenge getDummyChallenge(String challengeName) {
        Challenge challenge = new Challenge();
        challenge.setChallengeName(challengeName);
        challenge.setId(0L);
        challenge.setSubmissions(Collections.emptySet());
        return challenge;
    }

    public PLanguage getDummyPLanguage(String language) {
        PLanguage pLanguage = new PLanguage();
        pLanguage.setLanguage(language);
        pLanguage.setId(0L);
        pLanguage.setSubmissions(Collections.emptySet());
        return pLanguage;
    }

    public Contest getDummyContest(String contestName) {
        Contest contest = new Contest();
        contest.setName(contestName);
        contest.setId(0L);
        contest.setSubmissions(Collections.emptySet());
        return contest;
    }
}
