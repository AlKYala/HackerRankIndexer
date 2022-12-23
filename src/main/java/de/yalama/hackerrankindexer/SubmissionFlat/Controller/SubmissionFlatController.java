package de.yalama.hackerrankindexer.SubmissionFlat.Controller;

import de.yalama.hackerrankindexer.SubmissionFlat.Model.SubmissionFlat;
import de.yalama.hackerrankindexer.SubmissionFlat.Service.SubmissionFlatService;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/submissionFlat")
@RestController
@RequiredArgsConstructor
public class SubmissionFlatController implements BaseController<SubmissionFlat, Long> {

    @Autowired
    private SubmissionFlatService submissionFlatService;

    @Override
    @GetMapping
    public List<SubmissionFlat> findAll() {
        return this.submissionFlatService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public SubmissionFlat findById(@PathVariable Long id) throws HackerrankIndexerException {
        return this.submissionFlatService.findById(id);
    }

    @Override
    @PostMapping
    public SubmissionFlat create(SubmissionFlat submissionFlat) throws HackerrankIndexerException {
        return this.submissionFlatService.save(submissionFlat);
    }

    @Override
    @PutMapping("/{id}")
    public SubmissionFlat update(Long id, SubmissionFlat submissionFlat) throws HackerrankIndexerException {
        return this.submissionFlatService.update(id, submissionFlat);
    }

    @Override
    @DeleteMapping("/{id}")
    public Long delete(Long id) throws HackerrankIndexerException {
        return this.submissionFlatService.deleteById(id);
    }
}
