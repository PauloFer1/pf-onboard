package com.pfernand.pfonboard.pfonboard.core;

import com.pfernand.pfonboard.pfonboard.core.exception.CachedUserNotFound;
import com.pfernand.pfonboard.pfonboard.core.model.User;
import com.pfernand.pfonboard.pfonboard.core.validation.ValidateUserOnboard;
import com.pfernand.pfonboard.pfonboard.port.secondary.BoardUser;
import com.pfernand.pfonboard.pfonboard.port.secondary.OnBoardDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Named;

@Slf4j
@Named
@RequiredArgsConstructor
public class OnBoardService {

    private final BoardUser boardUser;
    private final OnBoardDao onBoardDao;
    private final ValidateUserOnboard validateUserOnboard;

    public User startOnboarding(final User user) {
        log.info("Starting Onboarding for user {}", user);
        validateUserOnboard.validateEmailAndNames(user);
        onBoardDao.cacheUser(user);
        return user;
    }

    public User onboardUser(final User user) {
        log.info("Onboarding user {}", user);
        validateUserOnboard.validatePassword(getCachedUser(user));
        boardUser.createUser(user);
        return user;
    }

    private User getCachedUser(final User user) {
        final User cachedUser = onBoardDao.getUserFromEmail(user.getEmail())
                .orElseThrow(() -> new CachedUserNotFound(user.getEmail()));
        return User.builder()
                .email(cachedUser.getEmail())
                .firstName(cachedUser.getFirstName())
                .lastName(cachedUser.getLastName())
                .password(user.getPassword())
                .build();
    }
}
