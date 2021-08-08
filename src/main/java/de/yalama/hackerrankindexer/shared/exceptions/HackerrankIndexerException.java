package de.yalama.hackerrankindexer.shared.exceptions;

/**
 * An abstract exception that all Exceptions in this project inherit
 * Used for easier typing and readability especially for multiple exception declaration in signature
 */
public abstract class HackerrankIndexerException extends RuntimeException {
    public HackerrankIndexerException() {
        super();
    }

    public HackerrankIndexerException(String message) {
        super(message);
    }
}
