package de.yalama.hackerrankindexer.shared.services;

import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;

import java.util.List;

/**
 * An interface that has to be implemented by all data model services
 * This interface offers all basic CRUD operations
 * @param <T> generic parameter for the used data type, must be a subclass of BaseEntity.class
 */
public interface BaseService<T extends BaseEntity> {
 /**
  * Read Operation: Query for instances by id
  * @param id The id to query an instance for
  * @return The instance or null when no instance with ID exists
  * @throws HackerrankIndexerException IDInvalidException
  * IDInvalidException when the passed ID is invalid (< 0)
  */
 T findById(Long id) throws HackerrankIndexerException;

 /**
  * Finds all instances of T
  * @return All instances of type T persisted in DB
  * @throws HackerrankIndexerException
  */
     List<T> findAll() throws HackerrankIndexerException;


    /**
     * Persists an instance into DB
  * @param instance The instance to persist
  * @return param instance when successful
  * @throws HackerrankIndexerException NotSavedException
  * Thrown when an instance was not persisted into DB
  */
 T save(T instance) throws HackerrankIndexerException;

 /**
  * Finds an Instance by ID and replaces it with the fitting ID
  * @param id The id of the instance to replace
  * @param instance The instnace to replace the current instance with passesd
  * @return param instance
  * @throws HackerrankIndexerException IDInvalidException, NotFoundException, NotSavedException
  * IDInvalidException when the id is invalid (< 0)
  * NotFoundException when no instance with given ID can be found
  * NotSavedException when the update fails
  */
     T update(Long id, T instance) throws HackerrankIndexerException;

 /**
  * Deletes the instance that has the matching ID
  * @param id The id of the instance that is deleted
  * @return param id
  * @throws HackerrankIndexerException IDInvalidException, NotDeletedException, NotFoundException
  * IDInvalidException when the ID is invalid (< 0)
  * NotDeletedException when no deletion can be done
  * NotFoundException when no instance with ID can be found
  */
 Long deleteById(Long id) throws HackerrankIndexerException;
}
