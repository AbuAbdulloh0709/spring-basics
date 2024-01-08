package com.epam.esm.exceptions;

/**
 * {@code NoSuchEntityException} is used when entity not found in database.
 *
 */

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException() {
    }

    public NoSuchEntityException(String messageCode) {
        super(messageCode);
    }

    public NoSuchEntityException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }
}
