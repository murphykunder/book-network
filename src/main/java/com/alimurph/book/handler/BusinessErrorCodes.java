package com.alimurph.book.handler;

import org.springframework.http.HttpStatus;

public enum BusinessErrorCodes {

    NO_CODE("0", "No code", HttpStatus.NOT_IMPLEMENTED),
    INCORRECT_CURRENT_PASSWORD("300", "Current password is incorrect", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_DOES_NOT_MATCH("301", "The new password does not match", HttpStatus.BAD_REQUEST),
    ACCOUNT_LOCKED("302", "User account is locked", HttpStatus.FORBIDDEN),
    ACCOUNT_DISABLED("303", "User account is disabled", HttpStatus.FORBIDDEN),
    BAD_CREDENTIALS("304", "Login username and / or password is incorrect", HttpStatus.BAD_REQUEST)
    ;

    private final String code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(String code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
