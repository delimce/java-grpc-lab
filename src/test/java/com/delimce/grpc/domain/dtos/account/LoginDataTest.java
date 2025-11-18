package com.delimce.grpc.domain.dtos.account;

import static org.junit.jupiter.api.Assertions.*;

import com.delimce.grpc.TestHandler;
import com.delimce.grpc.account.LoginRequest;
import javax.management.InvalidAttributeValueException;
import org.junit.jupiter.api.Test;

class LoginDataTest {

    @Test
    void testValidLoginData() {
        // Given
        String email = TestHandler.faker().internet().emailAddress();
        String password = TestHandler.faker().internet().password(6, 20);

        LoginRequest request = LoginRequest.newBuilder()
            .setEmail(email)
            .setPassword(password)
            .build();

        // When
        LoginData loginData = new LoginData(request);

        // Then
        assertDoesNotThrow(() -> loginData.getData());
        assertEquals(email, loginData.getEmail());
        assertEquals(password, loginData.getPassword());
    }

    @Test
    void testInvalidEmail() {
        // Given
        LoginRequest request = LoginRequest.newBuilder()
            .setEmail("invalid-email") // Invalid email format
            .setPassword(TestHandler.faker().internet().password(6, 20))
            .build();

        // When
        LoginData loginData = new LoginData(request);

        // Then
        InvalidAttributeValueException exception = assertThrows(
            InvalidAttributeValueException.class,
            () -> loginData.getData()
        );
        assertEquals("Invalid password, or email.", exception.getMessage());
    }

    @Test
    void testNullEmail() {
        // Given
        LoginRequest request = LoginRequest.newBuilder()
            .setPassword(TestHandler.faker().internet().password(6, 20))
            .build(); // email will be empty string (default for proto)

        // When
        LoginData loginData = new LoginData(request);

        // Then
        InvalidAttributeValueException exception = assertThrows(
            InvalidAttributeValueException.class,
            () -> loginData.getData()
        );
        assertEquals("Invalid password, or email.", exception.getMessage());
    }

    @Test
    void testInvalidEmailWithoutAtSymbol() {
        // Given
        LoginRequest request = LoginRequest.newBuilder()
            .setEmail("emailwithoutatsign.com")
            .setPassword(TestHandler.faker().internet().password(6, 20))
            .build();

        // When
        LoginData loginData = new LoginData(request);

        // Then
        InvalidAttributeValueException exception = assertThrows(
            InvalidAttributeValueException.class,
            () -> loginData.getData()
        );
        assertEquals("Invalid password, or email.", exception.getMessage());
    }

    @Test
    void testInvalidEmailWithoutDomain() {
        // Given
        LoginRequest request = LoginRequest.newBuilder()
            .setEmail("email@")
            .setPassword(TestHandler.faker().internet().password(6, 20))
            .build();

        // When
        LoginData loginData = new LoginData(request);

        // Then
        InvalidAttributeValueException exception = assertThrows(
            InvalidAttributeValueException.class,
            () -> loginData.getData()
        );
        assertEquals("Invalid password, or email.", exception.getMessage());
    }

    @Test
    void testPasswordTooShort() {
        // Given
        LoginRequest request = LoginRequest.newBuilder()
            .setEmail(TestHandler.faker().internet().emailAddress())
            .setPassword("12345") // Only 5 characters, needs 6
            .build();

        // When
        LoginData loginData = new LoginData(request);

        // Then
        InvalidAttributeValueException exception = assertThrows(
            InvalidAttributeValueException.class,
            () -> loginData.getData()
        );
        assertEquals("Invalid password, or email.", exception.getMessage());
    }

    @Test
    void testNullPassword() {
        // Given
        LoginRequest request = LoginRequest.newBuilder()
            .setEmail(TestHandler.faker().internet().emailAddress())
            .build(); // password will be empty string (default for proto)

        // When
        LoginData loginData = new LoginData(request);

        // Then
        InvalidAttributeValueException exception = assertThrows(
            InvalidAttributeValueException.class,
            () -> loginData.getData()
        );
        assertEquals("Invalid password, or email.", exception.getMessage());
    }

    @Test
    void testPasswordExactlyMinimumLength() {
        // Given
        String email = TestHandler.faker().internet().emailAddress();
        String password = "123456"; // Exactly 6 characters

        LoginRequest request = LoginRequest.newBuilder()
            .setEmail(email)
            .setPassword(password)
            .build();

        // When
        LoginData loginData = new LoginData(request);

        // Then
        assertDoesNotThrow(() -> loginData.getData());
        assertEquals(email, loginData.getEmail());
        assertEquals(password, loginData.getPassword());
    }

    @Test
    void testBothEmailAndPasswordInvalid() {
        // Given
        LoginRequest request = LoginRequest.newBuilder()
            .setEmail("invalid-email")
            .setPassword("12345") // Too short
            .build();

        // When
        LoginData loginData = new LoginData(request);

        // Then
        InvalidAttributeValueException exception = assertThrows(
            InvalidAttributeValueException.class,
            () -> loginData.getData()
        );
        assertEquals("Invalid password, or email.", exception.getMessage());
    }

    @Test
    void testValidEmailWithVariousFormats() {
        // Given
        String[] validEmails = {
            "user@example.com",
            "user.name@example.com",
            "user+tag@example.co.uk",
            "user_name@example-domain.com",
            "123@example.com",
        };

        for (String email : validEmails) {
            LoginRequest request = LoginRequest.newBuilder()
                .setEmail(email)
                .setPassword(TestHandler.faker().internet().password(6, 20))
                .build();

            // When
            LoginData loginData = new LoginData(request);

            // Then
            assertDoesNotThrow(
                () -> loginData.getData(),
                "Email should be valid: " + email
            );
        }
    }

    @Test
    void testGetDataReturnsSameInstance()
        throws Exception, com.delimce.grpc.domain.exceptions.DomainException {
        // Given
        String email = TestHandler.faker().internet().emailAddress();
        String password = TestHandler.faker().internet().password(6, 20);

        LoginRequest request = LoginRequest.newBuilder()
            .setEmail(email)
            .setPassword(password)
            .build();

        LoginData loginData = new LoginData(request);

        // When
        LoginData result = loginData.getData();

        // Then
        assertSame(
            loginData,
            result,
            "getData() should return the same instance"
        );
    }

    @Test
    void testEmptyEmailString() {
        // Given
        LoginRequest request = LoginRequest.newBuilder()
            .setEmail("")
            .setPassword(TestHandler.faker().internet().password(6, 20))
            .build();

        // When
        LoginData loginData = new LoginData(request);

        // Then
        InvalidAttributeValueException exception = assertThrows(
            InvalidAttributeValueException.class,
            () -> loginData.getData()
        );
        assertEquals("Invalid password, or email.", exception.getMessage());
    }

    @Test
    void testEmptyPasswordString() {
        // Given
        LoginRequest request = LoginRequest.newBuilder()
            .setEmail(TestHandler.faker().internet().emailAddress())
            .setPassword("")
            .build();

        // When
        LoginData loginData = new LoginData(request);

        // Then
        InvalidAttributeValueException exception = assertThrows(
            InvalidAttributeValueException.class,
            () -> loginData.getData()
        );
        assertEquals("Invalid password, or email.", exception.getMessage());
    }
}
