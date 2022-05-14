package de.yalama.hackerrankindexer.Submission.Controller;

import de.yalama.hackerrankindexer.Security.service.HeaderService;
import de.yalama.hackerrankindexer.Submission.Model.FilterRequest;
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
        //TODO make findByUserData
        return null;
    }

    @GetMapping("/{id}")
    public Submission findByIdByUser(@PathVariable Long id, HttpServletRequest request) throws HackerrankIndexerException {
        //TODO or delete
        return null;
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

    @PostMapping("/filter")
    public Collection<Submission> getFilterRequest(HttpServletRequest httpServletRequest, @RequestBody FilterRequest filterRequest) {
        //TODO needed?
        return null;
    }
}
