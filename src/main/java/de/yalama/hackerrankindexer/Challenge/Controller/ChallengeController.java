package de.yalama.hackerrankindexer.Challenge.Controller;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Challenge.Service.ChallengeService;
import de.yalama.hackerrankindexer.Session.Service.SessionService;
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
    private SessionService sessionService;

    //TODO diesen endpunkt schliessen
    @Override
    @GetMapping("/all")
    public List<Challenge> findAll() {
        return this.challengeService.findAll();
    }

    @GetMapping()
    public List<Challenge> findAllBySession(HttpServletRequest httpServletRequest) {
        return this.challengeService.getAllChallengesBySessionId(this.getSessionId(httpServletRequest));
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
    public Set<Submission> findSubmissionsByChallengeId(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return this.challengeService.getSubmissionsByChallengeIdAndSessionId(id, this.getSessionId(httpServletRequest));
    }

    @GetMapping("/{id}/ispassed")
    public Boolean checkIsSubmissionPassed(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return this.challengeService.checkIsChallengePassedBySessionId(id, this.getSessionId(httpServletRequest));
    }

    @GetMapping("/passed")
    public List<Challenge> getPassedChallenges(HttpServletRequest httpServletRequest) {
        return this.challengeService.getAllPassedChallengesBySessionId(this.getSessionId(httpServletRequest));
    }

    private long getSessionId(HttpServletRequest httpServletRequest) {
        return this.sessionService.getCurrentSessionId(httpServletRequest);
    }

}
