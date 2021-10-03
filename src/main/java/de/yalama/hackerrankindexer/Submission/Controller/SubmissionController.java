package de.yalama.hackerrankindexer.Submission.Controller;

import de.yalama.hackerrankindexer.Session.Service.SessionService;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/submission")
@RestController
@RequiredArgsConstructor
public class SubmissionController implements BaseController<Submission, Long> {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public List<Submission> findAllBySessionId(HttpServletRequest httpServletRequest) {
        long sessionId = this.sessionService.getCurrentSessionId(httpServletRequest);
        return this.submissionService.findAllBySessionId(sessionId);
    }

    @GetMapping("bySessionId")
    public List<Submission> findAllBySubmissionId(HttpServletRequest httpServletRequest) {
        long sessionId = this.sessionService.getCurrentSessionId(httpServletRequest);
        return this.submissionService.findAllBySessionId(sessionId);
    }

    //TODO close this
    @Override
    public List<Submission> findAll() {
        return this.submissionService.findAll();
    }

    //TODO man sollte nur dann eine submission einsehen koennen wenn die sessionId passt!
    @Override
    @GetMapping("/{id}")
    public Submission findById(@PathVariable Long id) throws HackerrankIndexerException {
        return this.submissionService.findById(id);
    }

    /* TODO change to sessionId
    @GetMapping("/user/{id}")
    public List<Submission> findByUserId(@PathVariable Long id) throws HackerrankIndexerException {
        return this.submissionService.findAllByUserId(id);
    }*/

    @Override
    @PostMapping
    public Submission create(@RequestBody Submission submission) throws HackerrankIndexerException {
        return this.submissionService.save(submission);
    }

    @Override
    @PutMapping("/{id}")
    public Submission update(@PathVariable Long id, @RequestBody Submission submission) throws HackerrankIndexerException {
        return this.submissionService.update(id, submission);
    }

    @Override
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) throws HackerrankIndexerException {
        return this.submissionService.deleteById(id);
    }

    @GetMapping("/passed")
    public List<Submission> getPassedSubmissions(long sessionId) {
        return this.submissionService.getAllPassed(sessionId);
    }

    /**
     * Returns the latest passed submission of each challenge
     * @return a List of Submission instances where only the latest passed Submission
     * of each challenge is
     */
    @GetMapping("/passed/latest")
    public List<Submission> getLatestPassedSubmissions(long sessionId) {
        return this.submissionService.getLastPassedFromAll(sessionId);
    }
}
