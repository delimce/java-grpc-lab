package com.delimce.grpc.domain.dtos.account;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.delimce.grpc.account.RegistrationRequest;
import javax.management.InvalidAttributeValueException;
import com.delimce.grpc.TestHandler;

class RegistrationDataTest {

    @Test
    void testValidRegistrationData() {
        // Given
        String name = TestHandler.faker().name().firstName();
        String lastName = TestHandler.faker().name().lastName();
        String email = TestHandler.faker().internet().emailAddress();

        RegistrationRequest request = RegistrationRequest.newBuilder()
                .setName(name)
                .setLastName(lastName)
                .setEmail(email)
                .build();

        // When
        RegistrationData registrationData = new RegistrationData(request);

        // Then
        assertDoesNotThrow(() -> registrationData.getData());
        assertEquals(name, registrationData.getName());
        assertEquals(lastName, registrationData.getLastName());
        assertEquals(email, registrationData.getEmail());
    }

    @Test
    void testInvalidName() {
        // Given: numeric name generated via faker's number API
        String numericName = TestHandler.faker().number().digits(3);
        RegistrationRequest request = RegistrationRequest.newBuilder()
                .setName(numericName) // Invalid name with numbers
                .setLastName(TestHandler.faker().name().lastName())
                .setEmail(TestHandler.faker().internet().emailAddress())
                .build();

        // When
        RegistrationData registrationData = new RegistrationData(request);

        // Then
        InvalidAttributeValueException exception = assertThrows(InvalidAttributeValueException.class,
                () -> registrationData.getData());
        assertTrue(exception.getMessage().contains("Invalid registration"));
    }

    @Test
    void testInvalidLastName() {
        // Given
        RegistrationRequest request = RegistrationRequest.newBuilder()
                .setName(TestHandler.faker().name().firstName())
                .setLastName("") // Empty last name
                .setEmail(TestHandler.faker().internet().emailAddress())
                .build();

        // When
        RegistrationData registrationData = new RegistrationData(request);

        // Then
        InvalidAttributeValueException exception = assertThrows(InvalidAttributeValueException.class,
                () -> registrationData.getData());
        assertTrue(exception.getMessage().contains("Invalid registration"));
    }

    @Test
    void testInvalidEmail() {
        // Given
        RegistrationRequest request = RegistrationRequest.newBuilder()
                .setName(TestHandler.faker().name().firstName())
                .setLastName(TestHandler.faker().name().lastName())
                .setEmail(TestHandler.faker().lorem().word()) // Invalid email format
                .build();

        // When
        RegistrationData registrationData = new RegistrationData(request);

        // Then
        InvalidAttributeValueException exception = assertThrows(InvalidAttributeValueException.class,
                () -> registrationData.getData());
        assertTrue(exception.getMessage().contains("Invalid registration"));
    }
}
