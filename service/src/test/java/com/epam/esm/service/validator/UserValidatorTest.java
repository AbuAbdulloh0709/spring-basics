package com.epam.esm.service.validator;

import com.epam.esm.entity.User;
import com.epam.esm.exceptions.ExceptionResult;
import com.epam.esm.validator.UserValidator;
import org.junit.jupiter.api.Test;

import static com.epam.esm.entity.Role.USER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidatorTest {
    private static final String INCORRECT_EMAIL = "email.com";
    private static final String CORRECT_EMAIL = "email@email.com";
    private static final String INCORRECT_PASSWORD = "123";
    private static final String CORRECT_PASSWORD = "12345678";

    private static final User INCORRECT_USER = new User(1, "name", INCORRECT_EMAIL, INCORRECT_PASSWORD, USER);
    private static final User CORRECT_USER = new User(0, "name", CORRECT_EMAIL, CORRECT_PASSWORD, USER);

    @Test
    void testValidate_incorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        UserValidator.validate(INCORRECT_USER, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidate_correctData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        UserValidator.validate(CORRECT_USER, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidateEmail_incorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        UserValidator.validateEmail(INCORRECT_EMAIL, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidateEmail_correctData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        UserValidator.validateEmail(CORRECT_EMAIL, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidatePassword_incorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        UserValidator.validatePassword(INCORRECT_PASSWORD, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidatePassword_correctData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        UserValidator.validatePassword(CORRECT_PASSWORD, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }
}