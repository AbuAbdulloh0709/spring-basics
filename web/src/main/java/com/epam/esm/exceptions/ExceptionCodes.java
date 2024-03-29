package com.epam.esm.exceptions;

public enum ExceptionCodes {
    BAD_REQUEST_EXCEPTION(40001, "BAD_REQUEST"),
    UNAUTHORIZED_EXCEPTION(40101, "UNAUTHORIZED"),
    FORBIDDEN_EXCEPTION(40301, "FORBIDDEN"),
    NOT_FOUND_EXCEPTION(40401, "NOT_FOUND"),
    METHOD_NOT_ALLOWED_EXCEPTION(40501, "METHOD_NOT_ALLOWED"),
    CONFLICT_EXCEPTION(40901, "CONFLICT"),
    INTERNAL_SERVER_ERROR_EXCEPTION(50001, "INTERNAL_SERVER_ERROR");

    private final int code;
    private final String reasonPhrase;

    ExceptionCodes(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public int getCode() {
        return code;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(code).append(" ");
        sb.append(reasonPhrase);
        return sb.toString();
    }
}
