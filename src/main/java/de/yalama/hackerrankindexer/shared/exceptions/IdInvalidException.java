package de.yalama.hackerrankindexer.shared.exceptions;

/**
 * Used in CRUD operations (see BaseService.class) that have a parameter for ID
 */
public class IdInvalidException extends HackerrankIndexerException {

    public IdInvalidException(String message) {
        super(message);
    }
}
