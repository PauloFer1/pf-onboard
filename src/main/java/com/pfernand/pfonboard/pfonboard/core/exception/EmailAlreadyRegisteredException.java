package com.pfernand.pfonboard.pfonboard.core.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {

    private static final String TEMPLATE = "Email: %s already associated to a user.";

    public EmailAlreadyRegisteredException(final String email) {
        super(String.format(TEMPLATE, email));
    }
}
