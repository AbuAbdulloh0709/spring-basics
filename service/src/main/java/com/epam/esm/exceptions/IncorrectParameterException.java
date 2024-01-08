package com.epam.esm.exceptions;

/**
 * {@code IncorrectParameterException} is used when received parameters have unacceptable value.
 *
 */

public class IncorrectParameterException extends RuntimeException {
    private final ExceptionResult exceptionResult;

    public IncorrectParameterException(ExceptionResult exceptionResult) {
        this.exceptionResult = exceptionResult;
    }

    public ExceptionResult getExceptionResult() {
        return exceptionResult;
    }
}
