package de.yalama.hackerrankindexer.shared.controllers;

import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Controller interface for all models for all basic CRUD operations
 * @param <T> The type parameter for the type (subclass of BaseEntity)
 * @param <ID> The type parameter for the type used for ID (here: Long)
 */
public interface BaseController<T extends BaseEntity, ID> {

    /**
     * GET-Request - returns all instances of T saved
     * @return a list of all instances of type T saved in the database
     */
    List<T> findAll(HttpSession httpSession);

    /**
     * GET-Request to get an item with a specified ID
     * @param id the id of the object (of class T)
     * @return The found instance
     * @throws HackerrankIndexerException for thrown exceptions see BaseService::findById
     */
    T findById(@PathVariable ID id, HttpSession httpSession) throws HackerrankIndexerException;

    /**
     * POST-Request to persist instances in the database
     * @param t The instance to persist in the database
     * @return The persisted instance
     * @throws HackerrankIndexerException for thrown exceptions see BaseService::save
     */
    T create(@RequestBody T t) throws HackerrankIndexerException;

    /**
     * PUT-Request to place instance in the database
     * @param id The id of the instance that has to be replaced
     * @param t The new instance of Id id
     * @return The instance that is persised in the database
     * @throws HackerrankIndexerException for thrown exceptions see BaseService::update
     */
    T update(@PathVariable ID id, @RequestBody T t) throws HackerrankIndexerException;

    /**
     * DELETE-Request to remove entries from the database
     * @param id The id of the instance to remove from the database
     * @return the parameter id
     * @throws HackerrankIndexerException for thrown exceptions see BaseService::delete
     */
    ID delete(@PathVariable ID id) throws HackerrankIndexerException;
}
