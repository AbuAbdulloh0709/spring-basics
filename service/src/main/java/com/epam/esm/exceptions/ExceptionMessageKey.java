package com.epam.esm.exceptions;

/**
 * {@code ExceptionMessageKey} is a class that has exception message keys.
 */

public final class ExceptionMessageKey {
    public static final String BAD_ID = "identifiable.badID";
    public static final String ID_EXISTENCE = "identifiable.hasId";
    public static final String NO_ENTITY = "identifiable.noObject";

    public static final String BAD_SORT_TYPE = "sort.badSortType";

    public static final String BAD_TAG_NAME = "tag.badName";
    public static final String TAG_EXIST = "tag.alreadyExist";
    public static final String TAG_NOT_FOUND = "tag.notFound";
    public static final String TAG_USED = "tag.used";

    public static final String BAD_GIFT_CERTIFICATE_NAME = "certificate.badName";
    public static final String BAD_GIFT_CERTIFICATE_DESCRIPTION = "certificate.badDescription";
    public static final String BAD_GIFT_CERTIFICATE_PRICE = "certificate.badPrice";
    public static final String BAD_GIFT_CERTIFICATE_DURATION = "certificate.badDuration";
    public static final String GIFT_CERTIFICATE_NOT_FOUND = "certificate.notFound";
    public static final String GIFT_CERTIFICATE_EXIST = "certificate.alreadyExist";
    public static final String GIFT_CERTIFICATE_USED = "certificate.used";

    public static final String ORDER_NOT_FOUND = "order.notFound";

    public static final String BAD_USER_ID = "order.badUserID";
    public static final String BAD_GIFT_CERTIFICATE_ID = "order.badGiftCertificateID";

    public static final String USER_NOT_FOUND = "user.notFound";
    public static final String USER_EXIST = "user.alreadyExist";
    public static final String BAD_USER_NAME = "user.badName";
    public static final String BAD_USER_EMAIL = "user.badEmail";
    public static final String BAD_USER_PASSWORD = "user.dadPassword";

    public static final String INVALID_PAGINATION = "pagination.invalid";
}
