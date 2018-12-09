package com.pfernand.pfonboard.pfonboard.core.exception;

public class InvalidEmailException extends RuntimeException {

    private static final String TEMPLATE = "Invalid Email: %s";

    public InvalidEmailException(final String email) {
        super(String.format(TEMPLATE, email));
    }
}
