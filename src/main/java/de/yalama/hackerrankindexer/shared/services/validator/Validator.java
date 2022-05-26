package de.yalama.hackerrankindexer.shared.services.validator;

import de.yalama.hackerrankindexer.shared.exceptions.*;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

@Getter
@Setter
@Slf4j
/**
 * Inversion of control - instead of throwing the Exceptions in the services this service throws them
 * E represents a subclass of BaseEntity
 * T represents an instance of JpaRepsority - only works if its the repository for E
 */
public class Validator<E extends BaseEntity, T extends JpaRepository> {

    private String entityName;
    private T repository;

    public Validator(String entityName, T repository) {
        this.entityName = entityName;
        this.repository = repository;
    }

    /**
     * Logs and throws exception if ID is null
     *
     * @param id the id
     * @throws HackerrankIndexerException IDInvalidException
     */
    public void throwIfIdInvalid(Long id) throws HackerrankIndexerException {
        if (id == null || id < 0) {
            String message = "ID %d is invalid - it has to be a natural number >= 0";
            //log.info(message);
            throw new IdInvalidException(message);
        }
    }

    /**
     * Throws an Exception if an instance of ID is found
     * Used in BaseService::delete
     *
     * @param id
     * @throws HackerrankIndexerException NotDeletedException
     */
    public void throwIfExistsByID(Long id) throws HackerrankIndexerException {
        if (this.repository.existsById(id)) {
            String message = String.format("A %s instance with ID %d is found", this.entityName, id);
            //log.info(message);
            throw new NotDeletedException(message);
        }
    }

    /**
     * Throws an Exception if an instance of ID cannot be found
     * Used in BaseService::save, BaseService::update and BaseService::delete and BaseService::findById
     *
     * @param id
     * @param operation 0 for save, 1 for delete, 2 for findById
     * @throws HackerrankIndexerException NotSavedException, NotDeletedException, NotFoundException
     */
    public void throwIfNotExistsByID(Long id, ValidatorOperations operation) throws HackerrankIndexerException {
        if(!this.repository.existsById(id)) {
            String[] messageWrapper = new String[] {"%s! A %s instance with ID %d cannot be found!"};
            HackerrankIndexerException exception = null;

            switch(operation) {
                case SAVE: this.wrapMessage(messageWrapper, "Saving failed!", id);
                    exception = new NotSavedException(messageWrapper[0]); break;
                case DELETE: this.wrapMessage(messageWrapper, "Deletion failed!", id);
                    exception = new NotDeletedException(messageWrapper[0]); break;
                default: this.wrapMessage(messageWrapper, "Update failed!", id);
                    exception = new NotFoundException(messageWrapper[0]);
            }

            //log.info(messageWrapper[0]);
            throw exception;
        }

    }

    private void wrapMessage(String[] messageWrapper, String pre, Long id) {
        messageWrapper[0] = String.format(messageWrapper[0], pre, this.entityName, id);
    }
    /**
     * Throws an Exception if two IDs do not match
     * Used in BaseService::update
     *
     * @param id1
     * @param id2
     * @throws HackerrankIndexerException NotSavedException
     */
    public void throwIfIDSDiffer(Long id1, Long id2) throws HackerrankIndexerException {
        if(id1 == null || id2 == null) {
            throw new IdInvalidException("Cannot pass null objects as IDs");
        }
        if(id1.longValue() != id2.longValue()) {
            throw new IdInvalidException("IDs %d and %d don't match");
        }
    }

    public boolean checkStringNullOrBlank(String s) {
        return s == null || s.isBlank();
    }
}
