package com.epam.esm.validator;

import com.epam.esm.entity.User;
import com.epam.esm.exceptions.ExceptionResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.esm.exceptions.ExceptionMessageKey.*;

public final class UserValidator {
    private static final int MAX_LENGTH_NAME = 100;
    private static final int MIN_LENGTH_NAME = 1;
    private static final int MIN_LENGTH_PASSWORD = 8;
    private static final String EMAIL_PATTERN = "^.+@.+\\..+$";

    public static void validate(User user, ExceptionResult er) {
        IdentifiableValidator.validateExistenceOfId(user.getId(), er);
        validateEmail(user.getEmail(), er);
        validatePassword(user.getPassword(), er);
        validateName(user.getName(), er);
    }

    public static void validateEmail(String email, ExceptionResult er) {
        if (email == null) {
            er.addException(BAD_USER_EMAIL, email);
        } else {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                er.addException(BAD_USER_EMAIL, email);
            }
        }
    }

    public static void validatePassword(String password, ExceptionResult er) {
        if (password == null || password.length() < MIN_LENGTH_PASSWORD) {
            er.addException(BAD_USER_PASSWORD, password);
        }
    }

    private static void validateName(String name, ExceptionResult er) {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            er.addException(BAD_USER_NAME, name);
        }
    }
}
