package com.pfernand.pfonboard.pfonboard.core;

import com.pfernand.pfonboard.pfonboard.core.exception.CachedUserNotFound;
import com.pfernand.pfonboard.pfonboard.core.model.User;
import com.pfernand.pfonboard.pfonboard.core.validation.ValidateUserOnboard;
import com.pfernand.pfonboard.pfonboard.port.secondary.BoardUser;
import com.pfernand.pfonboard.pfonboard.port.secondary.OnBoardDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class OnBoardServiceTest {

    @Mock
    private BoardUser boardUser;

    @Mock
    private OnBoardDao onBoardDao;

    @Mock
    private ValidateUserOnboard validateUserOnboard;

    @InjectMocks
    private OnBoardService onBoardService;

    @Test
    public void startOnboarding() {
        // Given
        final User user = User.builder().email("test").build();

        // When
        Mockito.when(validateUserOnboard.validateEmailAndNames(user))
                .thenReturn(true);
        Mockito.doNothing().when(onBoardDao).cacheUser(user);
        onBoardService.startOnboarding(user);

        // Then
        Mockito.verify(validateUserOnboard, Mockito.times(1)).validateEmailAndNames(user);
        Mockito.verify(onBoardDao, Mockito.times(1)).cacheUser(user);
    }


    @Test
    public void onboardUserCachedNotFoundThrowsException() {
        // Given
        final User user = User.builder().email("test").build();

        // When
        Mockito.when(onBoardDao.getUserFromEmail(user.getEmail()))
                .thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(CachedUserNotFound.class)
                .isThrownBy(() -> onBoardService.onboardUser(user))
                .withMessage("User not found in cache: test");
    }

    @Test
    public void onboardUser() {
        // Given
        final User user = User.builder().email("test").build();

        // When
        Mockito.when(validateUserOnboard.validatePassword(user))
                .thenReturn(true);
        Mockito.when(onBoardDao.getUserFromEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        User userResult = onBoardService.onboardUser(user);

        // Then
        Mockito.verify(validateUserOnboard, Mockito.times(1)).validatePassword(user);
        Mockito.verify(boardUser, Mockito.times(1)).createUser(user);
        assertEquals(user, userResult);
    }
}