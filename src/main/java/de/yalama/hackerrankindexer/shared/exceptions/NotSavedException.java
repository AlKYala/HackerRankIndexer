package de.yalama.hackerrankindexer.shared.exceptions;

/**
 * Used in CRUD operations where new isntances are persisted or updated
 */
public class NotSavedException extends HackerrankIndexerException {

    public NotSavedException(String message) {
        super(message);
    }
}
