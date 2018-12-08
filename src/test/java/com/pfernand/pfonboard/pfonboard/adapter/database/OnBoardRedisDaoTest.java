package com.pfernand.pfonboard.pfonboard.adapter.database;

import com.pfernand.pfonboard.pfonboard.port.database.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.HashOperations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class OnBoardRedisDaoTest {

    private static final String EMAIL = "email@mail.com";
    private final static String USER_KEY = "USER";

    @Mock
    private HashOperations<String, String, User> hashOperations;

    @InjectMocks
    private OnBoardRedisDao onBoardRedisDao;

    @Test
    public void cacheUserCallsPut() {
        // Given
        final User user = User.builder()
                .email(EMAIL)
                .build();

        // When
        onBoardRedisDao.cacheUser(user);

        // Then
        Mockito.verify(hashOperations, Mockito.timeout(1))
                .put(USER_KEY, user.getEmail(), user);
    }

    @Test
    public void getUserFromEmailReturnEmptyOptional() {
        // Given
        final User user = User.builder()
                .email(EMAIL)
                .build();
        // When
        Mockito.when(hashOperations.get(USER_KEY, EMAIL))
                .thenReturn(user);
        Optional<User> optionalUser = onBoardRedisDao.getUserFromEmail(EMAIL);

        // Then
        assertEquals(Optional.of(user), optionalUser);
    }
}