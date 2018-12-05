package com.pfernand.pfonboard.pfonboard.core;

import com.pfernand.pfonboard.pfonboard.core.model.User;
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
public class OnBoardService {

    private static final String BEARER = "Bearer ";

    private final RestTemplate restTemplate;
    private final String userServiceUri;
    private final AppToken appToken;

    public OnBoardService(@Value("${user-service.create.uri}") final String userServiceUri, final RestTemplate restTemplate, final AppToken appToken) {
        this.userServiceUri = userServiceUri;
        this.restTemplate = restTemplate;
        this.appToken = appToken;
    }

    public User onboardUser(final User user) {
        log.info("Onboarding user {}", user);
        ResponseEntity<User> responseEntity = restTemplate.exchange(userServiceUri, HttpMethod.POST, generateRequest(user), User.class);
        return responseEntity.getBody();
    }

    private HttpEntity<User> generateRequest(final User user) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HttpHeaders.AUTHORIZATION, BEARER + appToken.generateAppToken());
        HttpEntity<User> request = new HttpEntity<>(user, httpHeaders);
        log.info(request.getHeaders().toString());
        return request;
    }
}
