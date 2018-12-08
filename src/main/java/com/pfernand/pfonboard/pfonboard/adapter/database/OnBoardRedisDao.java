package com.pfernand.pfonboard.pfonboard.adapter.database;

import com.pfernand.pfonboard.pfonboard.port.database.OnBoardDao;
import com.pfernand.pfonboard.pfonboard.port.database.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Primary
public class OnBoardRedisDao implements OnBoardDao {

    private final static String USER_KEY = "USER";
    private final HashOperations<String, String, User> hashOperations;

    @Inject
    public OnBoardRedisDao(final HashOperations hashOperations) {
        this.hashOperations = hashOperations;
    }

    @Override
    public void cacheUser(User user) {
        log.info("Caching User: {}", user);
        hashOperations.put(USER_KEY, user.getEmail(), user);
    }

    @Override
    public Optional<User> getUserFromEmail(String email) {
        return Optional.ofNullable(hashOperations.get(USER_KEY, email));
    }

    @Override
    public Long deleteUsers(List<User> users) {
        return hashOperations.delete(USER_KEY, users.stream()
                .map(User::getEmail)
                .toArray());
    }
}
