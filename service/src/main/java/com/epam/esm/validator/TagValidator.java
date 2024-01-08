package com.epam.esm.validator;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.ExceptionResult;

import static com.epam.esm.exceptions.ExceptionMessageKey.BAD_TAG_NAME;

public final class TagValidator {
    private static final int MAX_LENGTH_NAME = 20;
    private static final int MIN_LENGTH_NAME = 3;

    private TagValidator() {
    }

    public static void validate(Tag tag, ExceptionResult er) {
        IdentifiableValidator.validateExistenceOfId(tag.getId(), er);
        validateName(tag.getName(), er);
    }

    public static void validateName(String name, ExceptionResult er) {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            er.addException(BAD_TAG_NAME, name);
        }
    }
}
