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

    @GetMapping
    public Collection<Submission> findAllByUserData(HttpServletRequest request) {
        Long userDataId = Long.parseLong(request.getParameter("userDataId"));
        return this.submissionService.findAllByUserDataId(userDataId);
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

    @GetMapping("/pLanguage")
    public Set<Submission> getSubmissionsByLanguagesAndUserDataId(@RequestBody List<Long> ids, HttpServletRequest request) {
        Long userDataId = Long.parseLong(request.getParameter("userDataId"));
        return this.submissionService.getSubmissionsByLanguagesAndUserDataId(ids, userDataId);
    }

    @GetMapping("/{challengeId}/submissions")
    public List<Submission> findSubmissionsByChallengeId(@PathVariable Long challengeId, HttpServletRequest request) {
        Long userDataId = (Long) request.getAttribute("userDataId");
        return this.submissionService.getSubmissionsByChallengeIdAndUserDataId(challengeId, userDataId);
    }
}
