package com.pfernand.pfonboard.pfonboard.core.validation;

import com.pfernand.pfonboard.pfonboard.core.exception.EmailAlreadyRegisteredException;
import com.pfernand.pfonboard.pfonboard.core.exception.InvalidEmailException;
import com.pfernand.pfonboard.pfonboard.core.exception.InvalidNameException;
import com.pfernand.pfonboard.pfonboard.core.exception.InvalidPasswordException;
import com.pfernand.pfonboard.pfonboard.core.model.User;
import com.pfernand.pfonboard.pfonboard.port.secondary.UserCheck;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(MockitoJUnitRunner.class)
public class ValidateUserOnboardTest {

    @Mock
    private UserCheck userCheck;

    @InjectMocks
    private ValidateUserOnboard validateUserOnboard;

    @Test
    public void validateEmailAndNamesWithNullEmailThrowsException() {
        // Given
        final User user = User.builder()
                .firstName("Paulo")
                .lastName("Fernandes")
                .build();

        // When
        // Then
        assertThatExceptionOfType(InvalidEmailException.class)
                .isThrownBy(() -> validateUserOnboard.validateEmailAndNames(user))
                .withMessage("Invalid Email: null");
    }

    @Test
    public void validateEmailAndNamesWithEmptyEmailThrowsException() {
        // Given
        final User user = User.builder()
                .firstName("Paulo")
                .lastName("Fernandes")
                .email("")
                .build();

        // When
        // Then
        assertThatExceptionOfType(InvalidEmailException.class)
                .isThrownBy(() -> validateUserOnboard.validateEmailAndNames(user))
                .withMessage("Invalid Email: ");
    }

    @Test
    public void validateEmailAndNamesWithInvalidEmailNoAtThrowsException() {
        // Given
        final User user = User.builder()
                .firstName("Paulo")
                .lastName("Fernandes")
                .email("paulo.mail.com")
                .build();

        // When
        // Then
        assertThatExceptionOfType(InvalidEmailException.class)
                .isThrownBy(() -> validateUserOnboard.validateEmailAndNames(user))
                .withMessage("Invalid Email: paulo.mail.com");
    }

    @Test
    public void validateEmailAndNamesWithInvalidEmailNoDomainThrowsException() {
        // Given
        final User user = User.builder()
                .firstName("Paulo")
                .lastName("Fernandes")
                .email("paulo@mail.")
                .build();

        // When
        // Then
        assertThatExceptionOfType(InvalidEmailException.class)
                .isThrownBy(() -> validateUserOnboard.validateEmailAndNames(user))
                .withMessage("Invalid Email: paulo@mail.");
    }

    @Test
    public void validateEmailAndNamesWithInvalidEmailShortDomainThrowsException() {
        // Given
        final User user = User.builder()
                .firstName("Paulo")
                .lastName("Fernandes")
                .email("paulo@mail.p")
                .build();

        // When
        // Then
        assertThatExceptionOfType(InvalidEmailException.class)
                .isThrownBy(() -> validateUserOnboard.validateEmailAndNames(user))
                .withMessage("Invalid Email: paulo@mail.p");
    }

    @Test
    public void validateEmailAndNamesWithAlreadyRegisteredThrowsException() {
        // Given
        final User user = User.builder()
                .firstName("Paulo")
                .lastName("Fernandes")
                .email("paulo@mail.pt")
                .build();

        // When
        Mockito.when(userCheck.isEmailRegistered(user.getEmail()))
                .thenReturn(true);
        // Then
        assertThatExceptionOfType(EmailAlreadyRegisteredException.class)
                .isThrownBy(() -> validateUserOnboard.validateEmailAndNames(user))
                .withMessage("Email: paulo@mail.pt already associated to a user.");
    }

    @Test
    public void validateEmailAndNamesWithNullFirstNameThrowsException() {
        // Given
        final User user = User.builder()
                .lastName("Fernandes")
                .email("paulo@mail.pt")
                .build();

        // When
        Mockito.when(userCheck.isEmailRegistered(user.getEmail()))
                .thenReturn(false);

        // Then
        assertThatExceptionOfType(InvalidNameException.class)
                .isThrownBy(() -> validateUserOnboard.validateEmailAndNames(user))
                .withMessage("Invalid First Name: null");
    }

    @Test
    public void validateEmailAndNamesWithEmptyFirstNameThrowsException() {
        // Given
        final User user = User.builder()
                .firstName("")
                .lastName("Fernandes")
                .email("paulo@mail.pt")
                .build();

        // When
        Mockito.when(userCheck.isEmailRegistered(user.getEmail()))
                .thenReturn(false);

        // Then
        assertThatExceptionOfType(InvalidNameException.class)
                .isThrownBy(() -> validateUserOnboard.validateEmailAndNames(user))
                .withMessage("Invalid First Name: ");
    }

    @Test
    public void validateEmailAndNamesWithNullLastNameThrowsException() {
        // Given
        final User user = User.builder()
                .firstName("Paulo")
                .email("paulo@mail.pt")
                .build();

        // When
        Mockito.when(userCheck.isEmailRegistered(user.getEmail()))
                .thenReturn(false);

        // Then
        assertThatExceptionOfType(InvalidNameException.class)
                .isThrownBy(() -> validateUserOnboard.validateEmailAndNames(user))
                .withMessage("Invalid Last Name: null");
    }

    @Test
    public void validateEmailAndNamesWithEmptyLasttNameThrowsException() {
        // Given
        final User user = User.builder()
                .firstName("Paulo")
                .lastName("")
                .email("paulo@mail.pt")
                .build();

        // When
        Mockito.when(userCheck.isEmailRegistered(user.getEmail()))
                .thenReturn(false);

        // Then
        assertThatExceptionOfType(InvalidNameException.class)
                .isThrownBy(() -> validateUserOnboard.validateEmailAndNames(user))
                .withMessage("Invalid Last Name: ");
    }

    @Test
    public void validateEmailAndNames() {
        // Given
        final User user = User.builder()
                .firstName("Paulo")
                .lastName("Fernandes")
                .email("paulo@mail.pt")
                .build();

        // When
        Mockito.when(userCheck.isEmailRegistered(user.getEmail()))
                .thenReturn(false);
        boolean result = validateUserOnboard.validateEmailAndNames(user);

        // Then
        assertTrue(result);
    }

    @Test
    public void validatePasswordNullThrowsException() {
        // Given
        final User user = User.builder()
                .email("paulo@mail.pt")
                .build();

        // When
        // Then
        assertThatExceptionOfType(InvalidPasswordException.class)
                .isThrownBy(() -> validateUserOnboard.validatePassword(user))
                .withMessage("Invalid password, reason: Empty Password");
    }

    @Test
    public void validatePasswordEmptyThrowsException() {
        // Given
        final User user = User.builder()
                .email("paulo@mail.pt")
                .password("")
                .build();

        // When
        // Then
        assertThatExceptionOfType(InvalidPasswordException.class)
                .isThrownBy(() -> validateUserOnboard.validatePassword(user))
                .withMessage("Invalid password, reason: Empty Password");
    }

    @Test
    public void validatePasswordWitheSpacesThrowsException() {
        // Given
        final User user = User.builder()
                .email("paulo@mail.pt")
                .password("w s")
                .build();

        // When
        // Then
        assertThatExceptionOfType(InvalidPasswordException.class)
                .isThrownBy(() -> validateUserOnboard.validatePassword(user))
                .withMessage("Invalid password, reason: Password contains whitespaces");
    }

    @Test
    public void validatePassword() {
        // Given
        final User user = User.builder()
                .email("paulo@mail.pt")
                .password("password")
                .build();

        // When
        boolean result = validateUserOnboard.validatePassword(user);

        // Then
        assertTrue(result);
    }
}