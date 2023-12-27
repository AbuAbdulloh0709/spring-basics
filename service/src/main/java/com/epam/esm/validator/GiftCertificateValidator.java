package com.epam.esm.validator;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.ExceptionResult;

import java.math.BigDecimal;
import java.util.List;

import static com.epam.esm.exceptions.ExceptionMessageKey.*;

public final class GiftCertificateValidator {
    private static final int MAX_LENGTH_NAME = 45;
    private static final int MIN_LENGTH_NAME = 3;
    private static final int MAX_LENGTH_DESCRIPTION = 300;
    private static final int MAX_SCALE = 2;
    private static final BigDecimal MIN_PRICE = new BigDecimal("0.01");
    private static final BigDecimal MAX_PRICE = new BigDecimal("999999.99");
    private static final int MAX_DURATION = 366;
    private static final int MIN_DURATION = 1;

    public static void validate(GiftCertificate giftCertificate, ExceptionResult er) {
        IdentifiableValidator.validateExistenceOfId(giftCertificate.getId(), er);
        validateName(giftCertificate.getName(), er);
        validateDescription(giftCertificate.getDescription(), er);
        validatePrice(giftCertificate.getPrice(), er);
        validateDuration(giftCertificate.getDuration(), er);
        validateListOfTags(giftCertificate.getTags(), er);
    }

    public static void validateForUpdate(GiftCertificate giftCertificate, ExceptionResult er) {
        if (giftCertificate.getName() != null) {
            validateName(giftCertificate.getName(), er);
        }
        if (giftCertificate.getDescription() != null) {
            validateDescription(giftCertificate.getDescription(), er);
        }
        if (giftCertificate.getPrice() != null) {
            validatePrice(giftCertificate.getPrice(), er);
        }
        if (giftCertificate.getDuration() != 0) {
            validateDuration(giftCertificate.getDuration(), er);
        }
        validateListOfTags(giftCertificate.getTags(), er);
    }

    public static void validateListOfTags(List<Tag> tags, ExceptionResult er) {
        if (tags == null) return;
        for (Tag tag : tags) {
            TagValidator.validate(tag, er);
        }
    }

    public static void validateName(String name, ExceptionResult er) {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            er.addException(BAD_GIFT_CERTIFICATE_NAME, name);
        }
    }

    private static void validateDescription(String description, ExceptionResult er) {
        if (description == null || description.length() > MAX_LENGTH_DESCRIPTION) {
            er.addException(BAD_GIFT_CERTIFICATE_DESCRIPTION, description);
        }
    }

    private static void validatePrice(BigDecimal price, ExceptionResult er) {
        if (price == null || price.scale() > MAX_SCALE
                || price.compareTo(MIN_PRICE) < 0 || price.compareTo(MAX_PRICE) > 0) {
            er.addException(BAD_GIFT_CERTIFICATE_PRICE, price);
        }
    }

    private static void validateDuration(int duration, ExceptionResult er) {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            er.addException(BAD_GIFT_CERTIFICATE_DURATION, duration);
        }
    }
}
