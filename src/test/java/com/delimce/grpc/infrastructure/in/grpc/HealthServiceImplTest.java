package com.delimce.grpc.infrastructure.in.grpc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.delimce.grpc.health.Empty;
import com.delimce.grpc.health.PingResponse;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class HealthServiceImplTest {

    private final HealthServiceImpl service = new HealthServiceImpl();

    @Test
    void pingEmitsPongAndCompletes() {
        StreamObserver<PingResponse> observer = mockObserver();
        ArgumentCaptor<PingResponse> response = ArgumentCaptor.forClass(PingResponse.class);

        service.ping(Empty.getDefaultInstance(), observer);

        verify(observer).onNext(response.capture());
        assertEquals("Pong", response.getValue().getMessage());
        verify(observer).onCompleted();
        verify(observer, never()).onError(any());
    }

    @Test
    void pingMapsUnexpectedErrorsToUnavailable() {
        StreamObserver<PingResponse> observer = mockObserver();
        doThrow(new IllegalStateException("boom")).when(observer).onNext(any());

        service.ping(Empty.getDefaultInstance(), observer);

        ArgumentCaptor<Throwable> error = ArgumentCaptor.forClass(Throwable.class);
        verify(observer).onError(error.capture());
        StatusException statusException = (StatusException) error.getValue();
        assertEquals(Status.Code.UNAVAILABLE, statusException.getStatus().getCode());
        assertEquals("Service Unavailable", statusException.getStatus().getDescription());
        verify(observer, never()).onCompleted();
    }

    @SuppressWarnings("unchecked")
    private static <T> StreamObserver<T> mockObserver() {
        return mock(StreamObserver.class);
    }
}
