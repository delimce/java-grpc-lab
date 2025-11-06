package com.delimce.grpc.domain.exceptions;

public class DomainException extends Throwable {

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

}
