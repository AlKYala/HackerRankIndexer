package de.yalama.hackerrankindexer.shared.services;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import org.springframework.stereotype.Service;

@Service
public class DummyService {

    public Submission getDummySubmission() {
        Submission submission = new Submission();
        submission.setSessionId(0L);
        submission.setCode("dummycode");
        submission.setId(0L);
        submission.setScore(0.0);
        return submission;
    }

    public Challenge getDummyChallenge(String challengeName) {
        Challenge challenge = new Challenge();
        challenge.setChallengeName(challengeName);
        challenge.setId(0L);
        return challenge;
    }

    public PLanguage getPLanguage(String language) {
        PLanguage pLanguage = new PLanguage();
        pLanguage.setLanguage(language);
        pLanguage.setId(0L);
        return pLanguage;
    }
}
