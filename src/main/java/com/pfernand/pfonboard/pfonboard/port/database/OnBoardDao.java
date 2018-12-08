package com.pfernand.pfonboard.pfonboard.port.database;

import com.pfernand.pfonboard.pfonboard.port.database.model.User;

import java.util.List;
import java.util.Optional;

public interface OnBoardDao {
    void cacheUser(final User user);

    Optional<User> getUserFromEmail(final String email);

    Long deleteUsers(final List<User> users);
}
