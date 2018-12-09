package com.pfernand.pfonboard.pfonboard.core.exception;

public class CachedUserNotFound extends RuntimeException {

    private static final String TEMPLATE = "User not found in cache: %s";

    public CachedUserNotFound(final String email) {
        super(String.format(TEMPLATE, email));
    }
}
