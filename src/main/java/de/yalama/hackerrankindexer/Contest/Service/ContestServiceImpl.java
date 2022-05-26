package de.yalama.hackerrankindexer.Contest.Service;

import de.yalama.hackerrankindexer.Contest.Model.Contest;
import de.yalama.hackerrankindexer.Contest.Repository.ContestRepository;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.ServiceHandler;
import de.yalama.hackerrankindexer.shared.services.validator.Validator;
import de.yalama.hackerrankindexer.shared.services.validator.ValidatorOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContestServiceImpl extends ContestService {

    private ContestRepository contestRepository;
    private Validator<Contest, ContestRepository> validator;
    private ServiceHandler<Contest, ContestRepository> serviceHandler;

    public ContestServiceImpl(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
        this.validator = new Validator<Contest, ContestRepository>("Contest", contestRepository);
        this.serviceHandler = new ServiceHandler<Contest, ContestRepository>(this.contestRepository, this.validator);
    }

    @Override
    public Contest findById(Long id) throws HackerrankIndexerException {
        return this.serviceHandler.findById(id);
    }

    @Override
    public List<Contest> findAll() throws HackerrankIndexerException {
        return this.serviceHandler.findAll();
    }

    @Override
    public Contest save(Contest instance) throws HackerrankIndexerException {
        return this.serviceHandler.save(instance);
    }

    @Override
    public Contest update(Long id, Contest instance) throws HackerrankIndexerException {
        return this.serviceHandler.update(id, instance);
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        this.validator.throwIfNotExistsByID(id, ValidatorOperations.DELETE);
        this.findById(id).getSubmissions().forEach(submission -> submission.setContest(null));
        return this.serviceHandler.deleteById(id);
    }

    @Override
    public Contest persist(Contest contest) {
        Contest foundContest = this.findByName(contest.getName());
        if(foundContest != null) {
            return foundContest;
        }
        return this.save(contest);
    }

    @Override
    public Contest findByName(String contestName) {
        return this.contestRepository.findContestByName(contestName);
    }
}