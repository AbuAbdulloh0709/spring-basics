package com.epam.esm.exception;

public class DaoException extends RuntimeException {
    public DaoException() {
    }

    public DaoException(String messageCode) {
        super(messageCode);
    }

    public DaoException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
