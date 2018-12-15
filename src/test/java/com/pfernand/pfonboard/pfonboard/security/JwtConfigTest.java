package com.pfernand.pfonboard.pfonboard.security;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JwtConfigTest {

    private static final int EXPIRATION = 1000;
    private static final String SECRET = "secret";
    private static final String CHARSET = "UTF-8";


    @Test
    public void contructor() {
        // Given
        // When
        final JwtConfig jwtConfig = new JwtConfig(EXPIRATION, SECRET, CHARSET);

        // Then
        assertEquals(EXPIRATION, jwtConfig.getExpiration());
        assertEquals(SECRET, jwtConfig.getSecret());
        assertEquals(CHARSET, jwtConfig.getCharset());
    }
}