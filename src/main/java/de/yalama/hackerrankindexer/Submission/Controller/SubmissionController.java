package de.yalama.hackerrankindexer.Submission.Controller;

import de.yalama.hackerrankindexer.Session.Service.SessionService;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.DummyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/submission")
@RestController
@RequiredArgsConstructor
public class SubmissionController implements BaseController<Submission, Long> {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private DummyService dummyService;

    @GetMapping
    public List<Submission> findAll(HttpSession httpSession) {
        String sessionId = this.sessionService.getCurrentSessionId(httpSession);
        return this.submissionService.findAllBySessionId(sessionId);
    }

    @GetMapping("bySessionId")
    public List<Submission> findAllBySubmissionId(HttpSession httpSession) {
        String sessionId = this.sessionService.getCurrentSessionId(httpSession);
        return this.submissionService.findAllBySessionId(sessionId);
    }

    @GetMapping("/{id}")
    public Submission findById(@PathVariable Long id, HttpSession httpSession) throws HackerrankIndexerException {
        Submission submission = this.submissionService.findById(id);
        String currentSessionId = this.sessionService.getCurrentSessionId(httpSession);
        System.out.printf("SessionID: %d SubmissionSessionId: %d\n", currentSessionId, submission.getSessionId());
        if(!submission.getSessionId().equals(currentSessionId)) {
            return this.dummyService.getDummySubmission();
        }
        return submission;
    }

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
    public List<Submission> getPassedSubmissions(String sessionId) {
        return this.submissionService.getAllPassed(sessionId);
    }

    /**
     * Returns the latest passed submission of each challenge
     * @return a List of Submission instances where only the latest passed Submission
     * of each challenge is
     */
    @GetMapping("/passed/latest")
    public List<Submission> getLatestPassedSubmissions(String sessionId) {
        return this.submissionService.getLastPassedFromAll(sessionId);
    }
}
