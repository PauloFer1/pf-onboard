package com.pfernand.pfonboard.pfonboard.core.exception;

public class UnableToCreateUserException extends RuntimeException {

    private static final String TEMPLATE = "Error creating user: %s";

    public UnableToCreateUserException(final String email, Throwable t) {
        super(String.format(TEMPLATE, email, t));
    }
}
