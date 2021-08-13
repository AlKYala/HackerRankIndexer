package de.yalama.hackerrankindexer.PLanguage.Service;

import de.yalama.hackerrankindexer.PLanguage.Repository.PLanguageRepository;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.ServiceHandler;
import de.yalama.hackerrankindexer.shared.services.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PLanguage save(PLanguage instance) throws HackerrankIndexerException {
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
}
