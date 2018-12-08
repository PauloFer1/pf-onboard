package com.pfernand.pfonboard.pfonboard.adapter.secondary.rest;

import com.pfernand.pfonboard.pfonboard.core.exception.UnableToCreateUserException;
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
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(MockitoJUnitRunner.class)
public class BoardUserRestTest {

    private static final String SERVICE_URI = "service.com/user";
    private static final String TOKEN = "token";

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AppToken appToken;

    private BoardUserRest boardUserRest;

    @Before
    public void SetUp() {
        boardUserRest = new BoardUserRest(restTemplate, SERVICE_URI, appToken);
    }

    @Test
    public void createUserThrowsException() {
        // Given
        final User user = User.builder().email("test").build();

        // When
        Mockito.when(restTemplate.exchange(SERVICE_URI, HttpMethod.POST, generateRequest(user), User.class))
                .thenThrow(new RuntimeException("Unauthorized"));
        Mockito.when(appToken.generateAppToken()).thenReturn(TOKEN);

        // Then
        assertThatExceptionOfType(UnableToCreateUserException.class)
                .isThrownBy(() -> boardUserRest.createUser(user))
                .withMessageContaining("Error creating user: test");
    }

    @Test
    public void createUser() {
        // Given
        final User user = User.builder().email("test").build();

        // When
        Mockito.when(appToken.generateAppToken()).thenReturn(TOKEN);

        // Then
        boardUserRest.createUser(user);

        Mockito.verify(restTemplate, Mockito.times(1))
                .exchange(SERVICE_URI, HttpMethod.POST, generateRequest(user), User.class);
    }

    private HttpEntity<User> generateRequest(final User user) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN);
        return new HttpEntity<>(user, httpHeaders);
    }

}