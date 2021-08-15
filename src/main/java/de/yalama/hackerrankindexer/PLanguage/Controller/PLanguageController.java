package de.yalama.hackerrankindexer.PLanguage.Controller;

import de.yalama.hackerrankindexer.PLanguage.Service.PLanguageService;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planguage")
@RequiredArgsConstructor
public class PLanguageController implements BaseController<PLanguage, Long> {

    @Autowired
    private PLanguageService pLanguageService;

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
}
