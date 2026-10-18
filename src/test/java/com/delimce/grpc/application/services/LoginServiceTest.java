package com.delimce.grpc.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.delimce.grpc.account.LoginRequest;
import com.delimce.grpc.account.UserAccount;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;

class LoginServiceTest {

    private final LoginService loginService = new LoginService();

    @Test
    void loginReturnsAccountForValidCredentials() {
        LoginRequest request = LoginRequest.newBuilder()
            .setEmail("user@example.com")
            .setPassword("secret")
            .build();

        UserAccount account = loginService.login(request);

        assertEquals("user@example.com", account.getEmail());
        assertEquals("uid-" + Math.abs("user@example.com".hashCode()), account.getUid());
    }

    @Test
    void loginRejectsInvalidEmail() {
        assertInvalidCredentials("invalid-email", "secret");
    }

    @Test
    void loginRejectsShortPassword() {
        assertInvalidCredentials("user@example.com", "short");
    }

    private void assertInvalidCredentials(String email, String password) {
        LoginRequest request = LoginRequest.newBuilder()
            .setEmail(email)
            .setPassword(password)
            .build();

        StatusRuntimeException exception = assertThrows(
            StatusRuntimeException.class,
            () -> loginService.login(request)
        );

        assertEquals(Status.Code.INVALID_ARGUMENT, exception.getStatus().getCode());
        assertEquals("Invalid password, or email.", exception.getStatus().getDescription());
    }
}
