package com.epam.esm.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code ExceptionResult} designed to save multiple validation exception message codes with parameters.
 *
 */

public class ExceptionResult {
    private final Map<String, Object[]> exceptionMessages;

    public ExceptionResult() {
        exceptionMessages = new HashMap<>();
    }

    public void addException(String messageCode, Object... arguments) {
        exceptionMessages.put(messageCode, arguments);
    }

    public Map<String, Object[]> getExceptionMessages() {
        return exceptionMessages;
    }
}
