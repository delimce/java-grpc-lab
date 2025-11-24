package com.delimce.grpc.application.services;

import static org.junit.jupiter.api.Assertions.*;

import com.delimce.grpc.TestHandler;
import com.delimce.grpc.account.RegistrationRequest;
import com.delimce.grpc.account.RegistrationResponse;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;

    private RegistrationRequest validRequest;
    private RegistrationRequest invalidRequest;

    @BeforeEach
    void setUp() {
        // Set up a valid request
        String validName = TestHandler.faker().name().firstName();
        String validLastName = TestHandler.faker().name().lastName();
        String validEmail = TestHandler.faker().internet().emailAddress();

        validRequest = RegistrationRequest.newBuilder()
            .setName(validName)
            .setLastName(validLastName)
            .setEmail(validEmail)
            .build();

        // Set up an invalid request
        String numericName = TestHandler.faker().number().digits(3);
        invalidRequest = RegistrationRequest.newBuilder()
            .setName(numericName) // Invalid name with numbers
            .setLastName(TestHandler.faker().name().lastName())
            .setEmail(TestHandler.faker().lorem().word()) // invalid email
            .build();
    }

    @Test
    void testSuccessfulRegistration() {
        // When
        RegistrationResponse response = registrationService.register(
            validRequest
        );

        // Then
        assertTrue(response.getSuccess());
        assertEquals("User registered successfully", response.getMessage());
    }

    @Test
    void testFailedRegistrationWithInvalidName() {
        // When & Then
        StatusRuntimeException exception = assertThrows(
            StatusRuntimeException.class,
            () -> {
                registrationService.register(invalidRequest);
            }
        );

        assertEquals(
            Status.INVALID_ARGUMENT.getCode(),
            exception.getStatus().getCode()
        );
        assertTrue(exception.getMessage().contains("Invalid registration"));
    }

    @Test
    void testFailedRegistrationWithEmptyFields() {
        // Given
        RegistrationRequest emptyRequest = RegistrationRequest.newBuilder()
            .setName("")
            .setLastName("")
            .setEmail("")
            .build();

        // When & Then
        StatusRuntimeException exception = assertThrows(
            StatusRuntimeException.class,
            () -> {
                registrationService.register(emptyRequest);
            }
        );

        assertEquals(
            Status.INVALID_ARGUMENT.getCode(),
            exception.getStatus().getCode()
        );
        assertTrue(exception.getMessage().contains("Invalid registration"));
    }

    @Test
    void testFailedRegistrationWithInvalidEmail() {
        // Given
        RegistrationRequest invalidEmailRequest =
            RegistrationRequest.newBuilder()
                .setName("John")
                .setLastName("Doe")
                .setEmail("not-an-email")
                .build();

        // When & Then
        StatusRuntimeException exception = assertThrows(
            StatusRuntimeException.class,
            () -> {
                registrationService.register(invalidEmailRequest);
            }
        );

        assertEquals(
            Status.INVALID_ARGUMENT.getCode(),
            exception.getStatus().getCode()
        );
        assertTrue(exception.getMessage().contains("Invalid registration"));
    }

    @Test
    void testFailedRegistrationWithNullFields() {
        // Given
        RegistrationRequest nullFieldsRequest =
            RegistrationRequest.newBuilder().build();

        // When & Then
        StatusRuntimeException exception = assertThrows(
            StatusRuntimeException.class,
            () -> {
                registrationService.register(nullFieldsRequest);
            }
        );

        assertEquals(
            Status.INVALID_ARGUMENT.getCode(),
            exception.getStatus().getCode()
        );
        assertTrue(exception.getMessage().contains("Invalid registration"));
    }
}
