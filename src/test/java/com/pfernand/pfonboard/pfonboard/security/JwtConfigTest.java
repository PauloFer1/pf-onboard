package com.pfernand.pfonboard.pfonboard.security;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JwtConfigTest {

    private static final int EXPIRATION = 1000;
    private static final String SECRET = "secret";

    private JwtConfig jwtConfig;


    @Test
    public void contructor() {
        // Given
        // When
        jwtConfig = new JwtConfig(EXPIRATION, SECRET);

        // Then
        assertEquals(EXPIRATION, jwtConfig.getExpiration());
        assertEquals(SECRET, jwtConfig.getSecret());
    }
}