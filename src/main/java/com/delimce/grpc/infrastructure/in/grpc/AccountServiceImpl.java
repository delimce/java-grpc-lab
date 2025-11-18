package com.delimce.grpc.infrastructure.in.grpc;

import com.delimce.grpc.account.AccountServiceGrpc;
import com.delimce.grpc.account.LoginRequest;
import com.delimce.grpc.account.RegistrationRequest;
import com.delimce.grpc.account.RegistrationResponse;
import com.delimce.grpc.account.UserAccount;
import com.delimce.grpc.application.ports.RegistrationServiceInterface;
import com.delimce.grpc.application.ports.LoginServiceInterface;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class AccountServiceImpl extends AccountServiceGrpc.AccountServiceImplBase {

    private static final String ERROR_MESSAGE = "Service Unavailable";

    private final RegistrationServiceInterface registrationService;
    private final LoginServiceInterface loginService;

    @Override
    public void register(RegistrationRequest request, StreamObserver<RegistrationResponse> responseObserver) {
        try {
            var response = registrationService.register(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error(ERROR_MESSAGE, e);
            responseObserver.onError(io.grpc.Status.UNAVAILABLE
                    .withDescription(ERROR_MESSAGE)
                    .asException());
        }
    }

    @Override
    public void login(LoginRequest request, StreamObserver<UserAccount> responseObserver) {
        try {
            var response = loginService.login(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error(ERROR_MESSAGE, e);
            responseObserver.onError(io.grpc.Status.UNAVAILABLE
                    .withDescription(ERROR_MESSAGE)
                    .asException());
        }
    };

}
