package de.yalama.hackerrankindexer.PLanguage.Service;

import de.yalama.hackerrankindexer.PLanguage.Repository.PLanguageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.ColorPickerUtil;
import de.yalama.hackerrankindexer.shared.services.ServiceHandler;
import de.yalama.hackerrankindexer.shared.services.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PLanguageServiceImpl extends PLanguageService {

    private PLanguageRepository pLangaugeRepository;
    private Validator<PLanguage, PLanguageRepository> validator;
    private ServiceHandler<PLanguage, PLanguageRepository> serviceHandler;

    public PLanguageServiceImpl(PLanguageRepository pLanguageRepository) {
        this.pLangaugeRepository = pLanguageRepository;
        this.validator =
                new Validator<PLanguage, PLanguageRepository>("Programming Language", pLanguageRepository);
        this.serviceHandler =
                new ServiceHandler<PLanguage, PLanguageRepository>(this.pLangaugeRepository, this.validator);
    }

    @Override
    public PLanguage findById(Long id) throws HackerrankIndexerException {
        return this.serviceHandler.findById(id);
    }

    @Override
    public List<PLanguage> findAll() throws HackerrankIndexerException {
        return this.serviceHandler.findAll();
    }

    public PLanguage persist(PLanguage instance) {
        PLanguage foundInstance = this.findByName(instance.getLanguage());
        if(foundInstance != null) {
            return foundInstance;
        }
        return this.save(instance);
    }

    @Override
    public Collection<Submission> getPassedSubmissionsForLanguage(Long languageId, String sessionId) {
        return this.getAllSubmissionsByLanguage(languageId, sessionId).stream().filter(submission -> submission.getScore() == 1)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Submission> getPassedSubmissionsForAllLanguages(Long[] languageIds, String sessionId) {
        List<Submission> submissions = new ArrayList<Submission>();
        for(Long languageId: languageIds) {
            submissions.addAll(this.getPassedSubmissionsForLanguage(languageId, sessionId));
        }
        return submissions;
    }

    @Override
    public Collection<Submission> getFailedSubmissionsForLanguage(Long languageId, String sessionId) {
        return this.getAllSubmissionsByLanguage(languageId, sessionId)
                .stream()
                .filter(submission -> submission.getScore() < 1)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Submission> getFailedSubmissionsForAllLanguages(Long[] languageIds, String sessionId) {
        List<Submission> submissions = new ArrayList<Submission>();
        for(Long languageId: languageIds) {
            submissions.addAll(this.getFailedSubmissionsForLanguage(languageId, sessionId));
        }
        return submissions;
    }

    @Override
    public Collection<Submission> getAllSubmissionsByLanguage(Long languageId, String sessionId) {
        return this.findById(languageId).getSubmissions().stream().filter(submission -> submission.getSessionId().equals(sessionId))
                .collect(Collectors.toList());
    }

    @Override
    public PLanguage save(PLanguage instance) throws HackerrankIndexerException {
        /*int colorValue = ((int) (16777215f * Math.random()));
        String color = String.format("#%s", Integer.toHexString((colorValue)));
        instance.setColor(color);*/
        log.info("saving language: {}", instance.getLanguage());
        instance.setColor(ColorPickerUtil.getNextColor());
        return this.serviceHandler.save(instance);
    }

    @Override
    public PLanguage update(Long id, PLanguage instance) throws HackerrankIndexerException {
        return this.serviceHandler.update(id, instance);
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, 1);
        this.findById(id).getSubmissions().forEach(submission -> submission.setLanguage(null));
        return this.serviceHandler.deleteById(id);
    }

    @Override
    public List<Submission> findSubmissionsOfLanguageAndSessionId(Long id, String sessionId) {
        PLanguage pLanguage = this.findById(id);
        return pLanguage.getSubmissions()
                .stream()
                .filter(submission -> submission.getSessionId().equals(sessionId))
                .collect(Collectors.toList());
    }

    @Override
    public List<PLanguage> findPLanguagesUsedBySessionId(String sessionId) {
        return this.findAll()
                .stream()
                .filter(pLanguage -> this.checkPLanguageHasSubmissionBySessionId(pLanguage, sessionId))
                .collect(Collectors.toList());
    }

    @Override
    public PLanguage findByName(String name) {
        for(PLanguage pLanguage : this.findAll()) {
            if(pLanguage.getLanguage().equals(name)) {
                return pLanguage;
            }
        }
        return null;
    }

    @Override
    public boolean checkExistsByName(String name) {
        return this.findByName(name) != null;
    }

    private boolean checkPLanguageHasSubmissionBySessionId(PLanguage pLanguage, String sessionId) {
        return pLanguage.getSubmissions().stream().anyMatch(submission -> submission.getSessionId().equals(sessionId));
    }
}
