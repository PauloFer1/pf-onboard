package com.pfernand.pfonboard.pfonboard.adapter.secondary;

import com.pfernand.pfonboard.pfonboard.adapter.secondary.redis.OnBoardRedisDao;
import com.pfernand.pfonboard.pfonboard.core.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.HashOperations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class OnBoardRedisDaoTest {

    private static final String EMAIL = "email@mail.com";
    private final static String USER_KEY = "USER";
    final User user = User.builder()
            .email(EMAIL)
            .build();

    @Mock
    private HashOperations<String, String, User> hashOperations;

    @InjectMocks
    private OnBoardRedisDao onBoardRedisDao;

    @Test
    public void cacheUserCallsPut() {
        // Given
        // When
        onBoardRedisDao.cacheUser(user);

        // Then
        Mockito.verify(hashOperations, Mockito.timeout(1))
                .put(USER_KEY, user.getEmail(), user);
    }

    @Test
    public void getUserFromEmailReturnEmptyOptional() {
        // Given
        // When
        Mockito.when(hashOperations.get(USER_KEY, EMAIL))
                .thenReturn(user);
        Optional<User> optionalUser = onBoardRedisDao.getUserFromEmail(EMAIL);

        // Then
        assertEquals(Optional.of(user), optionalUser);
    }

    @Test
    public void deleteUsers() {
        // Given
        final List<User> users = Collections.singletonList(user);

        // When
        Mockito.when(hashOperations.delete(USER_KEY, users.stream().map(User::getEmail).toArray()))
                .thenReturn(1L);
        Long result = onBoardRedisDao.deleteUsers(users);

        // Then
        assertEquals(1, result.intValue());
        Mockito.verify(hashOperations, Mockito.times(1))
                .delete(USER_KEY, users.stream().map(User::getEmail).toArray());
    }
}