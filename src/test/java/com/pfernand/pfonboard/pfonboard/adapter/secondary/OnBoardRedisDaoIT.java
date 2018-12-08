package com.pfernand.pfonboard.pfonboard.adapter.secondary;

import com.pfernand.pfonboard.pfonboard.PfOnboardApplication;
import com.pfernand.pfonboard.pfonboard.adapter.secondary.redis.OnBoardRedisDao;
import com.pfernand.pfonboard.pfonboard.core.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.test.context.junit4.SpringRunner;
import redis.embedded.RedisServer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PfOnboardApplication.class})
public class OnBoardRedisDaoIT {

    private final static String USER_KEY = "USER";
    private static final String EMAIL = "email@mail.com";

    private final User user = User.builder()
            .email(EMAIL)
            .build();

    private RedisServer redisServer;

    @Autowired
    private OnBoardRedisDao onBoardRedisDao;

    @Autowired
    private HashOperations<String, String, User> hashOperations;

    @Before
    public void setUp() throws Exception {
        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @After
    public void tearDown() {
        onBoardRedisDao.deleteUsers(Collections.singletonList(user));
        redisServer.stop();
    }

    @Test
    public void cacheUser() {
        // Given
        // When
        onBoardRedisDao.cacheUser(user);
        Optional<User> optionalUser = onBoardRedisDao.getUserFromEmail(EMAIL);

        // Then
        assertEquals(user, optionalUser.get());
    }

    @Test
    public void getUserFromEmailReturnsEmpty() {
        // Given
        // When
        Optional<User> optionalUser = onBoardRedisDao.getUserFromEmail(EMAIL);

        // Then
        assertEquals(Optional.empty(), optionalUser);
    }

    @Test
    public void deleteUsersDeleteOnlySelected() {
        // Given
        final User user2 = User.builder()
                .email("test2@mail.com")
                .build();
        final User user3 = User.builder()
                .email("test3@mail.com")
                .build();
        onBoardRedisDao.cacheUser(user);
        onBoardRedisDao.cacheUser(user2);
        onBoardRedisDao.cacheUser(user3);

        // When
        Long deleted = onBoardRedisDao.deleteUsers(Arrays.asList(user, user2));
        Map<String, User> users = hashOperations.entries(USER_KEY);

        // Then
        assertEquals(2, deleted.intValue());
        assertEquals(1, users.size());
        assertEquals(user3, users.get("test3@mail.com"));
    }
}
