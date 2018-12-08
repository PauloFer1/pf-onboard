package com.pfernand.pfonboard.pfonboard.port.secondary;

import com.pfernand.pfonboard.pfonboard.core.exception.UnableToCreateUserException;
import com.pfernand.pfonboard.pfonboard.core.model.User;

public interface BoardUser {
    void createUser(final User user) throws UnableToCreateUserException;
}
