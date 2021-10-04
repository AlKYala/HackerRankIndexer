package de.yalama.hackerrankindexer.Contest.Controller;

import de.yalama.hackerrankindexer.Contest.Model.Contest;
import de.yalama.hackerrankindexer.Contest.Service.ContestService;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/contest")
@RequiredArgsConstructor
public class ContestController implements BaseController<Contest, Long> {

    @Autowired
    private ContestService contestService;

    @Override
    @GetMapping
    public List<Contest> findAll(HttpServletRequest httpServletRequest) {
        return this.contestService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public Contest findById(@PathVariable Long id, HttpServletRequest httpServletRequest) throws HackerrankIndexerException {
        return this.contestService.findById(id);
    }

    @Override
    @PostMapping
    public Contest create(@RequestBody Contest contest) throws HackerrankIndexerException {
        return this.contestService.save(contest);
    }

    @Override
    @PutMapping("/{id}")
    public Contest update(@PathVariable Long id, @RequestBody Contest contest) throws HackerrankIndexerException {
        return this.contestService.update(id, contest);
    }

    @Override
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) throws HackerrankIndexerException {
        return this.contestService.deleteById(id);
    }
}
