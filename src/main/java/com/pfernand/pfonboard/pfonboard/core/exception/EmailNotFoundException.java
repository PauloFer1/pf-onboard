package com.pfernand.pfonboard.pfonboard.core.exception;

public class EmailNotFoundException extends RuntimeException {

    private static final String TEMPLATE = "Email not found in cache: %s";

    public EmailNotFoundException(final String email) {
        super(String.format(TEMPLATE, email));
    }
}
