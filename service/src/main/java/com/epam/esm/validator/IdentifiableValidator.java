package com.epam.esm.validator;

import com.epam.esm.exceptions.ExceptionResult;

import java.util.Objects;

import static com.epam.esm.exceptions.ExceptionMessageKey.*;

public final class IdentifiableValidator {
    private static final int MIN_ID = 1;
    private static final int EMPTY_ID = 0;

    private IdentifiableValidator() {
    }

    public static void validateId(long id, ExceptionResult er) {
        if (id < MIN_ID) {
            er.addException(BAD_ID, id);
        }
    }

    public static void validateExistenceOfId(long id, ExceptionResult er) {
        if (id != EMPTY_ID) {
            er.addException(ID_EXISTENCE);
        }
    }
    public static void validateSortType(String sortType, ExceptionResult er) {
        if (!(Objects.equals("ASC", sortType) ||
                Objects.equals("DESC", sortType))) {
            er.addException(BAD_SORT_TYPE, sortType);
        }
    }
}
