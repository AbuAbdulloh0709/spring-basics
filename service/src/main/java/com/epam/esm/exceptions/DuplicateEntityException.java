package com.epam.esm.exceptions;


/**
 * {@code DuplicateEntityException} is used when entity already exists in  database.
 *
 * @see RuntimeException
 */

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException() {
    }

    public DuplicateEntityException(String messageCode) {
        super(messageCode);
    }

    public DuplicateEntityException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }
}
