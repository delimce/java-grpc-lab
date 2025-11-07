package com.delimce.grpc.domain.dtos.commons;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.delimce.grpc.TestHandler;

class ValidationDataTest {

    private static class TestValidationData extends ValidationData {
        public boolean testEmailIsValid(String email) {
            return emailIsValid(email);
        }

        public boolean testPasswordIsValid(String password) {
            return passwordIsValid(password);
        }

        public boolean testIsNotBlank(String field) {
            return isNotBlank(field);
        }

        public boolean testIsString(Object obj) {
            return isString(obj);
        }
    }

    private final TestValidationData validator = new TestValidationData();

    @Test
    void testValidEmail() {
        assertTrue(validator.testEmailIsValid(TestHandler.faker().internet().emailAddress()));
        assertTrue(validator.testEmailIsValid(TestHandler.faker().internet().emailAddress()));
        assertTrue(validator.testEmailIsValid(TestHandler.faker().internet().emailAddress()));
    }

    @Test
    void testInvalidEmail() {
        assertFalse(validator.testEmailIsValid(TestHandler.faker().lorem().word()));
        assertFalse(validator.testEmailIsValid("@domain.com"));
        assertFalse(validator.testEmailIsValid("user@"));
        assertFalse(validator.testEmailIsValid(null));
    }

    @Test
    void testValidPassword() {
        assertTrue(validator.testPasswordIsValid(TestHandler.faker().internet().password(6, 12)));
        assertTrue(validator.testPasswordIsValid("123456"));
    }

    @Test
    void testInvalidPassword() {
        assertFalse(validator.testPasswordIsValid(TestHandler.faker().internet().password(1, 4))); // Too short
        assertFalse(validator.testPasswordIsValid(""));
        assertFalse(validator.testPasswordIsValid(null));
    }

    @Test
    void testIsNotBlank() {
        assertTrue(validator.testIsNotBlank(TestHandler.faker().lorem().word()));
        assertTrue(validator.testIsNotBlank(" text with spaces "));

        assertFalse(validator.testIsNotBlank(""));
        assertFalse(validator.testIsNotBlank("   "));
        assertFalse(validator.testIsNotBlank(null));
    }

    @Test
    void testIsString() {
        assertTrue(validator.testIsString(TestHandler.faker().lorem().word()));
        assertTrue(validator.testIsString(TestHandler.faker().name().firstName()));

        assertFalse(validator.testIsString("123")); // Contains digits
        assertFalse(validator.testIsString("text123")); // Contains digits
        assertFalse(validator.testIsString("")); // Empty string
        assertFalse(validator.testIsString("   ")); // Only whitespace
        assertFalse(validator.testIsString(123)); // Number
        assertFalse(validator.testIsString(null)); // Null
    }
}