package com.epam.esm.exceptions;

/**
 * {@code ConstraintViolationException} is used when Entity intended to delete violates foreign key constraint.
 *
 */

public class ConstraintViolationException extends RuntimeException {

    public ConstraintViolationException() {
    }

    public ConstraintViolationException(String message) {
        super(message);
    }

}
