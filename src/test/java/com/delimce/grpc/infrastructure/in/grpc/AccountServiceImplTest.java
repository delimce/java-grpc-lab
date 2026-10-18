package com.delimce.grpc.infrastructure.in.grpc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.delimce.grpc.account.LoginRequest;
import com.delimce.grpc.account.RegistrationRequest;
import com.delimce.grpc.account.RegistrationResponse;
import com.delimce.grpc.account.UserAccount;
import com.delimce.grpc.application.ports.LoginServiceInterface;
import com.delimce.grpc.application.ports.RegistrationServiceInterface;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class AccountServiceImplTest {

    private RegistrationServiceInterface registrationService;
    private LoginServiceInterface loginService;
    private AccountServiceImpl service;

    @BeforeEach
    void setUp() {
        registrationService = mock(RegistrationServiceInterface.class);
        loginService = mock(LoginServiceInterface.class);
        service = new AccountServiceImpl(registrationService, loginService);
    }

    @Test
    void registerEmitsResponseAndCompletes() {
        RegistrationRequest request = RegistrationRequest.newBuilder().build();
        RegistrationResponse response = RegistrationResponse.newBuilder()
            .setSuccess(true)
            .build();
        StreamObserver<RegistrationResponse> observer = mockObserver();
        when(registrationService.register(request)).thenReturn(response);

        service.register(request, observer);

        verify(observer).onNext(response);
        verify(observer).onCompleted();
        verify(observer, never()).onError(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void registerForwardsGrpcErrors() {
        RegistrationRequest request = RegistrationRequest.newBuilder().build();
        StreamObserver<RegistrationResponse> observer = mockObserver();
        StatusRuntimeException failure = Status.INVALID_ARGUMENT.asRuntimeException();
        when(registrationService.register(request)).thenThrow(failure);

        service.register(request, observer);

        verify(observer).onError(failure);
        verify(observer, never()).onCompleted();
    }

    @Test
    void registerMapsUnexpectedErrorsToUnavailable() {
        RegistrationRequest request = RegistrationRequest.newBuilder().build();
        StreamObserver<RegistrationResponse> observer = mockObserver();
        when(registrationService.register(request)).thenThrow(new IllegalStateException("boom"));

        service.register(request, observer);

        assertUnavailableError(observer);
    }

    @Test
    void loginEmitsAccountAndCompletes() {
        LoginRequest request = LoginRequest.newBuilder().build();
        UserAccount account = UserAccount.newBuilder().setUid("42").build();
        StreamObserver<UserAccount> observer = mockObserver();
        when(loginService.login(request)).thenReturn(account);

        service.login(request, observer);

        verify(observer).onNext(account);
        verify(observer).onCompleted();
        verify(observer, never()).onError(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void loginForwardsGrpcErrors() {
        LoginRequest request = LoginRequest.newBuilder().build();
        StreamObserver<UserAccount> observer = mockObserver();
        StatusRuntimeException failure = Status.INVALID_ARGUMENT.asRuntimeException();
        when(loginService.login(request)).thenThrow(failure);

        service.login(request, observer);

        verify(observer).onError(failure);
        verify(observer, never()).onCompleted();
    }

    @Test
    void loginMapsUnexpectedErrorsToUnavailable() {
        LoginRequest request = LoginRequest.newBuilder().build();
        StreamObserver<UserAccount> observer = mockObserver();
        when(loginService.login(request)).thenThrow(new IllegalStateException("boom"));

        service.login(request, observer);

        assertUnavailableError(observer);
    }

    @SuppressWarnings("unchecked")
    private static <T> StreamObserver<T> mockObserver() {
        return mock(StreamObserver.class);
    }

    private static void assertUnavailableError(StreamObserver<?> observer) {
        ArgumentCaptor<Throwable> error = ArgumentCaptor.forClass(Throwable.class);
        verify(observer).onError(error.capture());
        StatusException statusException = (StatusException) error.getValue();
        assertEquals(Status.Code.UNAVAILABLE, statusException.getStatus().getCode());
        assertEquals("Service Unavailable", statusException.getStatus().getDescription());
        verify(observer, never()).onCompleted();
    }
}
