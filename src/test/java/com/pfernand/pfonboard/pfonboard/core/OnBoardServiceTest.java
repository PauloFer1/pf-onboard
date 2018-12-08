package com.pfernand.pfonboard.pfonboard.core;

import com.pfernand.pfonboard.pfonboard.core.model.User;
import com.pfernand.pfonboard.pfonboard.port.secondary.BoardUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class OnBoardServiceTest {

    @Mock
    private BoardUser boardUser;

    @InjectMocks
    private OnBoardService onBoardService;

    @Test
    public void onboardUser() {
        // Given
        final User user = User.builder().email("test").build();

        // When
        User userResult = onBoardService.onboardUser(user);

        // Then
        Mockito.verify(boardUser, Mockito.times(1)).createUser(user);
        assertEquals(user, userResult);
    }
}