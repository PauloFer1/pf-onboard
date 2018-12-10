package com.pfernand.pfonboard.pfonboard.adapter.primary;

import com.pfernand.pfonboard.pfonboard.core.OnBoardService;
import com.pfernand.pfonboard.pfonboard.core.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class OnBoardControllerTest {

    @Mock
    private OnBoardService onBoardService;

    @InjectMocks
    private OnBoardController onBoardController;

    @Test
    public void startOnboard() {
        // Given
        final User user = User.builder()
                .email("email@mail.com")
                .firstName("Paulo")
                .lastName("Fernandes")
                .build();

        // When
        Mockito.when(onBoardService.startOnboarding(user))
                .thenReturn(user);
        ResponseEntity<User> response = onBoardController.startOnboard(user);

        // Then
        Mockito.verify(onBoardService, Mockito.times(1))
                .startOnboarding(user);
        assertEquals(ResponseEntity.ok(user), response);
    }

    @Test
    public void createUser() {
        // Given
        final User user = User.builder()
                .email("email@mail.com")
                .firstName("Paulo")
                .lastName("Fernandes")
                .build();

        // When
        Mockito.when(onBoardService.onboardUser(user))
                .thenReturn(user);
        ResponseEntity<User> response = onBoardController.createUser(user);

        // Then
        Mockito.verify(onBoardService, Mockito.times(1))
                .onboardUser(user);
        assertEquals(ResponseEntity.ok(user), response);
    }
}