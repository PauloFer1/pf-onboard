package com.pfernand.pfonboard.pfonboard.core.exception;

public class InvalidNameException extends RuntimeException {

    private static final String TEMPLATE = "Invalid %s: %s";

    public InvalidNameException(final String property, final String name) {
        super(String.format(TEMPLATE, property, name));
    }
}
