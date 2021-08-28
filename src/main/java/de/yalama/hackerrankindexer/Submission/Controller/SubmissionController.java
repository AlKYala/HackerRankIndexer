package de.yalama.hackerrankindexer.Submission.Controller;

import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/submission")
@RestController
@RequiredArgsConstructor
public class SubmissionController implements BaseController<Submission, Long> {

    @Autowired

    private SubmissionService submissionService;

    @Override
    @GetMapping
    public List<Submission> findAll() {
        return this.submissionService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public Submission findById(@PathVariable Long id) throws HackerrankIndexerException {
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
    public List<Submission> getPassedSubmissions() {
        return this.submissionService.getAllPassed();
    }
}
