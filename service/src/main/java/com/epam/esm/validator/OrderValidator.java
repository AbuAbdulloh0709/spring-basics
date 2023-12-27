package com.epam.esm.validator;

import com.epam.esm.entity.Order;
import com.epam.esm.exceptions.ExceptionResult;

import static com.epam.esm.exceptions.ExceptionMessageKey.BAD_GIFT_CERTIFICATE_ID;
import static com.epam.esm.exceptions.ExceptionMessageKey.BAD_USER_ID;

public final class OrderValidator {
    private static final int MIN_ID = 1;

    public static void validate(Order order, ExceptionResult er) {
        IdentifiableValidator.validateExistenceOfId(order.getId(), er);
        validateUserId(order.getUser().getId(), er);
        validateGiftCertificateId(order.getGiftCertificate().getId(), er);
    }

    public static void validateUserId(long userId, ExceptionResult er) {
        if (userId < MIN_ID) {
            er.addException(BAD_USER_ID, userId);
        }
    }

    public static void validateGiftCertificateId(long giftCertificateId, ExceptionResult er) {
        if (giftCertificateId < MIN_ID) {
            er.addException(BAD_GIFT_CERTIFICATE_ID, giftCertificateId);
        }
    }
}
