package com.pfernand.pfonboard.pfonboard.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AppTokenTest {

    private static final int EXPIRATION = 1000;
    private static final String SECRET = "secret";

    @Mock
    private JwtConfig jwtConfig;

    @InjectMocks
    private AppToken appToken;

    @Test
    public void generateAppToken() {
        // Given
        // When
        Mockito.when(jwtConfig.getExpiration()).thenReturn(EXPIRATION);
        Mockito.when(jwtConfig.getSecret()).thenReturn(SECRET);
        String result = appToken.generateAppToken();

        // Then
        assertTrue(result.length() > 64);
        assertEquals(2, result.chars().filter(c -> c == '.').count());
    }

}