package com.pfernand.pfonboard.pfonboard.adapter.secondary.rest;

import com.pfernand.pfonboard.pfonboard.core.model.User;
import com.pfernand.pfonboard.pfonboard.security.AppToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class UserCheckRestTest {

    private static final String EMAIL = "paulo@mail.com";
    private static final String SERVICE_URI = "service.com/user/";
    private static final String TOKEN = "token";

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AppToken appToken;

    private UserCheckRest userCheckRest;

    @Before
    public void SetUp() {
        userCheckRest = new UserCheckRest(restTemplate, SERVICE_URI, appToken);
    }

    @Test
    public void isEmailRegisteredThrowsRestException() {
        // Given
        // When
        Mockito.when(restTemplate.exchange(SERVICE_URI + EMAIL, HttpMethod.GET, generateRequest(), User.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        Mockito.when(appToken.generateAppToken()).thenReturn(TOKEN);

        // Then
        assertThatExceptionOfType(HttpClientErrorException.class)
                .isThrownBy(() -> userCheckRest.isEmailRegistered(EMAIL))
                .withMessageContaining("401 UNAUTHORIZED");
    }

    @Test
    public void isEmailRegisteredReturnsfalse() {
        // Given
        // When
        Mockito.when(restTemplate.exchange(SERVICE_URI + EMAIL, HttpMethod.GET, generateRequest(), User.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        Mockito.when(appToken.generateAppToken()).thenReturn(TOKEN);
        boolean result = userCheckRest.isEmailRegistered(EMAIL);

        // Then
        assertFalse(result);
    }

    @Test
    public void isEmailRegisteredReturnstrue() {
        // Given
        // When
        Mockito.when(restTemplate.exchange(SERVICE_URI + EMAIL, HttpMethod.GET, generateRequest(), User.class))
                .thenReturn(ResponseEntity.ok(User.builder().build()));
        Mockito.when(appToken.generateAppToken()).thenReturn(TOKEN);
        boolean result = userCheckRest.isEmailRegistered(EMAIL);

        // Then
        assertTrue(result);
    }

    private HttpEntity<User> generateRequest() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN);
        return new HttpEntity<>(httpHeaders);
    }

}