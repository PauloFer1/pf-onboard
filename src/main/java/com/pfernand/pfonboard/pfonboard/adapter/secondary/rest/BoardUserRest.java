package com.pfernand.pfonboard.pfonboard.adapter.secondary.rest;

import com.pfernand.pfonboard.pfonboard.core.exception.UnableToCreateUserException;
import com.pfernand.pfonboard.pfonboard.core.model.User;
import com.pfernand.pfonboard.pfonboard.port.secondary.BoardUser;
import com.pfernand.pfonboard.pfonboard.security.AppToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;

@Slf4j
@Named
public class BoardUserRest implements BoardUser {

    private static final String BEARER = "Bearer ";

    private final RestTemplate restTemplate;
    private final String userServiceUri;
    private final AppToken appToken;

    public BoardUserRest(RestTemplate restTemplate, @Value("${user-service.create.uri}") String userServiceUri, AppToken appToken) {
        this.restTemplate = restTemplate;
        this.userServiceUri = userServiceUri;
        this.appToken = appToken;
    }

    @Override
    public void createUser(User user) throws UnableToCreateUserException {
        log.info("Onboarding user {}", user);
        try {
            restTemplate.exchange(userServiceUri, HttpMethod.POST, generateRequest(user), User.class);
        } catch (Exception ex) {
            throw new UnableToCreateUserException(user.getEmail(), ex);
        }
    }

    private HttpEntity<User> generateRequest(final User user) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HttpHeaders.AUTHORIZATION, BEARER + appToken.generateAppToken());
        return new HttpEntity<>(user, httpHeaders);
    }
}
