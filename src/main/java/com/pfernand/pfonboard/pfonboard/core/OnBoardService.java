package com.pfernand.pfonboard.pfonboard.core;

import com.pfernand.pfonboard.pfonboard.core.model.User;
import com.pfernand.pfonboard.pfonboard.port.secondary.BoardUser;
import com.pfernand.pfonboard.pfonboard.security.AppToken;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class OnBoardService {

    private final BoardUser boardUser;

    public User onboardUser(final User user) {
        log.info("Onboarding user {}", user);
        boardUser.createUser(user);
        return user;
    }
}
