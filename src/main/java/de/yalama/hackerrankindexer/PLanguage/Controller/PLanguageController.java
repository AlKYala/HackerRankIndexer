package de.yalama.hackerrankindexer.PLanguage.Controller;

import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Security.service.HeaderService;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/planguage")
@RequiredArgsConstructor
public class PLanguageController implements BaseController<PLanguage, Long> {

    @Autowired
    private PLanguageService pLanguageService;

    @Autowired
    private HeaderService headerService;

    @Override
    @GetMapping
    public List<PLanguage> findAll() {
        return this.pLanguageService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public PLanguage findById(@PathVariable Long id) throws HackerrankIndexerException {
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

    @GetMapping("/submissions")
    public Set<Submission> getSubmissionsByLanguage(@RequestBody Long[] ids, HttpServletRequest request) {
        return this.pLanguageService
                .findSubmissionsOfLanguageByUser(Arrays.stream(ids).collect(Collectors.toSet()), this.headerService.getUserFromHeader(request));
    }

    @GetMapping("/user")
    public Set<PLanguage> getLangaugesByUser(HttpServletRequest request) {
        return this.headerService.getUserFromHeader(request).getUsedPLanguages();
    }
}
