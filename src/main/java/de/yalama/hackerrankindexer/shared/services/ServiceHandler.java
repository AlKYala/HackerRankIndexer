package de.yalama.hackerrankindexer.shared.services;

import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * A Class to delegate the responsibilites of services (CRUD operations) to
 * For details on what the methods do, check BaseService<E>
 */
@Slf4j
public class ServiceHandler<E extends BaseEntity,  G extends JpaRepository<E, Long>> {

    private Validator<E, G> validator;
    private G repository;

    public ServiceHandler(G repository, Validator<E, G> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public E findById(Long id) {
        this.validator.throwIfNotExistsByID(id, 2);
        return this.repository.findById(id).get();
    }

    public List<E> findAll() {
        return this.repository.findAll();
    }

    public E save(E instance) {
        E saved = this.repository.save(instance);
        return saved;
    }

    public E update(Long id, E instance) {
        this.validator.throwIfIdInvalid(id);
        this.validator.throwIfIDSDiffer(id, instance.getId());
        this.validator.throwIfNotExistsByID(id, 0);
        return this.repository.save(instance);
    }

    /*
    Not used in N side in 1:N relations - here: submission because these entities have
    a more complicated business logic for deletion
     */
    public Long deleteById(Long id) {
        this.repository.deleteById(id);
        this.validator.throwIfExistsByID(id);
        return id;
    }
}
