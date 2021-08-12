package de.yalama.hackerrankindexer.Challenge.Service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Challenge.Repository.ChallengeRepository;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChallengeServiceImpl extends ChallengeService {

    private ChallengeRepository challengeRepository;

    public ChallengeServiceImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public Challenge findById(Long id) throws HackerrankIndexerException {
        return null;
    }

    @Override
    public List<Challenge> findAll() throws HackerrankIndexerException {
        return null;
    }

    @Override
    public Challenge save(Challenge instance) throws HackerrankIndexerException {
        return null;
    }

    @Override
    public Challenge update(Long id, Challenge instance) throws HackerrankIndexerException {
        return null;
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        return null;
    }
}
