package de.yalama.hackerrankindexer.Challenge.Controller;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.Security.service.HeaderService;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge")
public class ChallengeController implements BaseController<Challenge, Long> {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private HeaderService headerService;

    @Override
    @GetMapping
    public List<Challenge> findAll() {
        return this.challengeService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public Challenge findById(@PathVariable Long id) throws HackerrankIndexerException {
        return this.challengeService.findById(id);
    }

    @Override
    @PostMapping
    public Challenge create(@RequestBody Challenge challenge) throws HackerrankIndexerException {
        return this.challengeService.save(challenge);
    }

    @Override
    @PutMapping("/{id}")
    public Challenge update(@PathVariable Long id, @RequestBody Challenge challenge) throws HackerrankIndexerException {
        return this.challengeService.update(id, challenge);
    }

    @Override
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) throws HackerrankIndexerException {
        return this.challengeService.deleteById(id);
    }

    @GetMapping("/{id}/submissions")
    public List<Submission> findSubmissionsByChallengeId(@PathVariable Long id, HttpServletRequest request) {
        //return this.challengeService.getSubmissionsByChallengeId(id, this.headerService.getUserFromHeader(request));
        //TODO make by UserData
        return null;
    }

    @GetMapping("/{id}/ispassed")
    public Boolean checkIsSubmissionPassed(@PathVariable Long id, HttpServletRequest request) {
        //return this.challengeService.checkIsChallengePassed(id, this.headerService.getUserFromHeader(request));
        //TODO make by UserData
        return null;
    }

    @GetMapping("/passed")
    public List<Challenge> getPassedChallenges(HttpServletRequest request) {
        //return this.challengeService.getAllPassedChallenges(this.headerService.getUserFromHeader(request));
        //TODO make by UserData
        return null;
    }

    @GetMapping("/failed")
    public List<Challenge> getFailedChallenges(HttpServletRequest request) {
        //return this.challengeService.getAllFailedChallenges(this.headerService.getUserFromHeader(request));
        //TODO make by UserData
        return null;
    }
}
