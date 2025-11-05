package com.delimce.grpc.infrastructure.in.grpc;

import com.delimce.grpc.health.Empty;
import com.delimce.grpc.health.PingResponse;
import com.delimce.grpc.health.HealthServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

@Log4j2
@Service
public class HealthServiceImpl extends HealthServiceGrpc.HealthServiceImplBase {

    private static final String ERROR_MESSAGE = "Service Unavailable";

    @Override
    public void ping(Empty request, StreamObserver<PingResponse> responseObserver) {
        try {
            PingResponse response = PingResponse.newBuilder()
                    .setMessage("Pong")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error(ERROR_MESSAGE, e);
            responseObserver.onError(io.grpc.Status.UNAVAILABLE
                    .withDescription(ERROR_MESSAGE)
                    .asException());
        }
    }
}