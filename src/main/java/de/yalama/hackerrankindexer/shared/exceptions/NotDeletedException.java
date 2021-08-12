package de.yalama.hackerrankindexer.shared.exceptions;

/**
 * Used in CRUD Operations where instances are removed
 */
public class NotDeletedException extends HackerrankIndexerException {
    public NotDeletedException(String message) {
        super(message);
    }
}
