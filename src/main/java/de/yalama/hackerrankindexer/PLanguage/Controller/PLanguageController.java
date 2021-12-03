package de.yalama.hackerrankindexer.PLanguage.Controller;

import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Session.Service.SessionService;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/planguage")
@RequiredArgsConstructor
public class PLanguageController implements BaseController<PLanguage, Long> {

    @Autowired
    private PLanguageService pLanguageService;

    @Autowired
    private SessionService sessionService;

    @Override
    @GetMapping
    public List<PLanguage> findAll(HttpSession httpSession) {
        return this.pLanguageService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public PLanguage findById(@PathVariable Long id, HttpSession httpSession) throws HackerrankIndexerException {
        return this.pLanguageService.findById(id);
    }

    @Override
    @PostMapping
    public PLanguage create(@RequestBody PLanguage pLanguage) throws HackerrankIndexerException {
        return this.pLanguageService.save(pLanguage);
    }

    @Override
    @PutMapping("/{id}")
    public PLanguage update(@PathVariable Long id, @RequestBody PLanguage pLanguage) throws HackerrankIndexerException {
        return this.pLanguageService.update(id, pLanguage);
    }

    @Override
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) throws HackerrankIndexerException {
        return this.pLanguageService.deleteById(id);
    }

    @GetMapping("/{id}/submissions")
    public List<Submission> getSubmissionsByLanguage(@PathVariable Long id, HttpSession httpSession) {
        String sessionId = this.sessionService.getCurrentSessionId(httpSession);
        return this.pLanguageService.findSubmissionsOfLanguageAndSessionId(id, sessionId);
    }

    @GetMapping("/{id}/submissions/passed")
    public Collection<Submission> getPassedSubmissionsByLanguage(@PathVariable Long id, HttpSession httpSession) {
        String sessionId = this.sessionService.getCurrentSessionId(httpSession);
        return this.pLanguageService.getPassedSubmissionsForLanguage(id, sessionId);
    }

    @GetMapping("/{id}/submissions/failed")
    public Collection<Submission> getFailedSubmissionsByLanguage(@PathVariable Long id, HttpSession httpSession) {
        String sessionId = this.sessionService.getCurrentSessionId(httpSession);
        return this.pLanguageService.getFailedSubmissionsForLanguage(id, sessionId);
    }

    @GetMapping("/submissions/passed")
    public Collection<Submission> getPassedSubmissionsByLanguage(@RequestBody Long[] ids, HttpSession httpSession) {
        String sessionId = this.sessionService.getCurrentSessionId(httpSession);
        return this.pLanguageService.getPassedSubmissionsForAllLanguages(ids, sessionId);
    }

    @GetMapping("/submissions/failed")
    public Collection<Submission> getFailedSubmissionsByLanguage(@RequestBody Long[] ids, HttpSession httpSession) {
        String sessionId = this.sessionService.getCurrentSessionId(httpSession);
        return this.pLanguageService.getFailedSubmissionsForAllLanguages(ids, sessionId);
    }
}
