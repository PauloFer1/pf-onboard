package com.pfernand.pfonboard.pfonboard.core.validation;

import com.pfernand.pfonboard.pfonboard.core.exception.InvalidEmailException;
import com.pfernand.pfonboard.pfonboard.core.exception.InvalidNameException;
import com.pfernand.pfonboard.pfonboard.core.exception.InvalidPasswordException;
import com.pfernand.pfonboard.pfonboard.core.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.inject.Named;
import java.util.regex.Pattern;

@Slf4j
@Named
public class ValidateUserOnboard {

    private static final String EMPTY_PASSWORD = "Empty Password";
    private static final String PASSWORD_CONTAINS_WHITESPACES = "Password contains whitespaces";

    private static final String FIRST_NAME = "First Name";
    private static final String LAST_NAME = "Last Name";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public boolean validateEmailAndNames(final User user) {
        validateEmail(user.getEmail());
        validateNames(user);
        return true;
    }

    public boolean validatePassword(final User user) {
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new InvalidPasswordException(EMPTY_PASSWORD);
        }
        if (StringUtils.containsWhitespace(user.getPassword())) {
            throw new InvalidPasswordException(PASSWORD_CONTAINS_WHITESPACES);
        }
        return true;
    }

    private void validateEmail(final String email) {
        if (null == email || !Pattern.compile(EMAIL_PATTERN).matcher(email).matches()) {
            throw new InvalidEmailException(email);
        }
    }

    private void validateNames(final User user) {
        if (null == user.getFirstName() || user.getFirstName().isEmpty()) {
            throw new InvalidNameException(FIRST_NAME, user.getFirstName());
        }
        if (null == user.getLastName() || user.getLastName().isEmpty()) {
            throw new InvalidNameException(LAST_NAME, user.getLastName());
        }
    }

}
