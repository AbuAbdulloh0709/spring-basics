package com.epam.esm.service.validator;

import com.epam.esm.exceptions.ExceptionResult;
import com.epam.esm.validator.IdentifiableValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdentifiableValidatorTest {
    private static final String INCORRECT_SORT_TYPE = "sort";
    private static final String CORRECT_SORT_TYPE = "ASC";
    private static final long INCORRECT_ID = -15;
    private static final long CORRECT_ID = 15;

    @Test
    void testValidateId_incorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(INCORRECT_ID, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidateId_correctData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(CORRECT_ID, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidateSortType_incorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateSortType(INCORRECT_SORT_TYPE, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidateSortType_correctData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateSortType(CORRECT_SORT_TYPE, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }
}