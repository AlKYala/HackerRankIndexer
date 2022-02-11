package de.yalama.hackerrankindexer.Submission.Controller;

import de.yalama.hackerrankindexer.Security.service.HeaderService;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequestMapping("/submission")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SubmissionController implements BaseController<Submission, Long> {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private HeaderService headerService;

    @GetMapping
    public Collection<Submission> findAllByUser(HttpServletRequest request) {
        return this.submissionService.findAllByUser(this.headerService.getUserFromHeader(request));
    }

    @GetMapping("/{id}")
    public Submission findByIdByUser(@PathVariable Long id, HttpServletRequest request) throws HackerrankIndexerException {
        User user = this.headerService.getUserFromHeader(request);
        Submission found =  this.submissionService.findById(id);
        if(found.getWriter().getId() != user.getId()) {
            throw new NotFoundException("User ID does not Match submission ID");
        }
        return found;
    }

    @Override
    public List<Submission> findAll() {
        return this.submissionService.findAll();
    }

    @Override
    public Submission findById(Long id) throws HackerrankIndexerException {
        return this.submissionService.findById(id);
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
    public List<Submission> getPassedSubmissions(HttpServletRequest request) {
        return this.submissionService.getAllPassed(this.headerService.getUserFromHeader(request));
    }

    /**
     * Returns the latest passed submission of each challenge
     * @return a List of Submission instances where only the latest passed Submission
     * of each challenge is
     */
    @GetMapping("/passed/latest")
    public Collection<Submission> getLatestPassedSubmissions(HttpServletRequest request) {
        return this.submissionService.getLastPassedFromAll(this.headerService.getUserFromHeader(request));
    }
}
