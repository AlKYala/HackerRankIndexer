package de.yalama.hackerrankindexer.Challenge.Service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Challenge.Repository.ChallengeRepository;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChallengeServiceImpl extends ChallengeService {

    private ChallengeRepository challengeRepository;
    private Validator<Challenge, ChallengeRepository> validator;

    public ChallengeServiceImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
        this.validator = new Validator<Challenge, ChallengeRepository>("Challenge", this.challengeRepository);
    }

    @Override
    public Challenge findById(Long id) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, 2);
        return this.challengeRepository.findById(id).get();
    }

    @Override
    public List<Challenge> findAll() throws HackerrankIndexerException {
        return this.challengeRepository.findAll();
    }

    @Override
    public Challenge save(Challenge instance) throws HackerrankIndexerException {
        this.validator.throwIfExistsByID(instance.getId());
        Challenge saved = this.challengeRepository.save(instance);
        this.validator.throwIfNotExistsByID(instance.getId(), 0);
        return saved;
    }

    @Override
    public Challenge update(Long id, Challenge instance) throws HackerrankIndexerException {
        this.validator.throwIfIdInvalid(id);
        this.validator.throwIfIDSDiffer(id, instance.getId());
        this.validator.throwIfNotExistsByID(id, 0);
        return this.challengeRepository.save(instance);
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, 1);
        this.challengeRepository.deleteById(id);
        this.validator.throwIfExistsByID(id);
        return id;
    }
}
