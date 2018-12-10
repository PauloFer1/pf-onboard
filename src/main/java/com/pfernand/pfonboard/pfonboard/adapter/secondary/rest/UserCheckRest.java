package com.pfernand.pfonboard.pfonboard.adapter.secondary.rest;

import com.pfernand.pfonboard.pfonboard.core.model.User;
import com.pfernand.pfonboard.pfonboard.port.secondary.UserCheck;
import com.pfernand.pfonboard.pfonboard.security.AppToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;

@Slf4j
@Named
public class UserCheckRest implements UserCheck {

    private static final String BEARER = "Bearer ";
    private static final int NOT_FOUND_CODE = 404;

    private final RestTemplate restTemplate;
    private final String userServiceUri;
    private final AppToken appToken;

    public UserCheckRest(RestTemplate restTemplate, @Value("${user-service.get.uri}") String userServiceUri, AppToken appToken) {
        this.restTemplate = restTemplate;
        this.userServiceUri = userServiceUri;
        this.appToken = appToken;
    }

    @Override
    public boolean isEmailRegistered(String email) {
        log.info("Verifying email: {}", email);
        boolean isRegistered = true;
        try {
            restTemplate.exchange(userServiceUri + email, HttpMethod.GET, generateRequest(), User.class);
        } catch (HttpClientErrorException ex) {
            isRegistered = handleHttpError(ex);
        }
        return isRegistered;
    }

    private HttpEntity<User> generateRequest() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HttpHeaders.AUTHORIZATION, BEARER + appToken.generateAppToken());
        return new HttpEntity<>(httpHeaders);
    }

    private boolean handleHttpError(HttpClientErrorException ex) {
        if (NOT_FOUND_CODE == ex.getRawStatusCode()) {
            return false;
        }
        throw ex;
    }
}
