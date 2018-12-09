package com.pfernand.pfonboard.pfonboard.core.exception;

public class InvalidPasswordException extends RuntimeException {

    private static final String TEMPLATE = "Invalid password, reason: %s";

    public InvalidPasswordException(final String reason) {
        super(String.format(TEMPLATE, reason));
    }
}
