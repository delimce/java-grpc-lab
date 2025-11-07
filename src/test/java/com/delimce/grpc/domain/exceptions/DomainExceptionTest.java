package com.delimce.grpc.domain.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.delimce.grpc.TestHandler;

class DomainExceptionTest {

    @Test
    void testDomainExceptionWithMessageAndCause() {
        // Given
        String errorMessage = TestHandler.faker().lorem().sentence();
        Exception cause = new RuntimeException(TestHandler.faker().lorem().sentence());

        // When
        DomainException exception = new DomainException(errorMessage, cause);

        // Then
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testDomainExceptionWithMessageAndNullCause() {
        // Given
        String errorMessage = TestHandler.faker().lorem().sentence();

        // When
        DomainException exception = new DomainException(errorMessage, null);

        // Then
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testDomainExceptionInheritance() {
        // When
        DomainException exception = new DomainException(TestHandler.faker().lorem().word(), null);

        // Then
        assertTrue(exception instanceof Throwable);
    }

    @Test
    void testDomainExceptionStackTrace() {
        // Given
        RuntimeException cause = new RuntimeException(TestHandler.faker().lorem().sentence());
        DomainException exception = new DomainException(TestHandler.faker().lorem().sentence(), cause);

        // Then
        assertArrayEquals(cause.getStackTrace(), exception.getCause().getStackTrace());
    }
}