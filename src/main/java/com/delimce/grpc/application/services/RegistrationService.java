package com.delimce.grpc.application.services;

import javax.management.InvalidAttributeValueException;
import io.grpc.Status;

import org.springframework.stereotype.Service;

import com.delimce.grpc.account.RegistrationRequest;
import com.delimce.grpc.account.RegistrationResponse;
import com.delimce.grpc.application.ports.RegistrationServiceInterface;
import com.delimce.grpc.domain.dtos.account.RegistrationData;
import com.delimce.grpc.domain.exceptions.DomainException;

@Service
public class RegistrationService implements RegistrationServiceInterface {

    @Override
    public RegistrationResponse register(RegistrationRequest request) {
        RegistrationResponse response = RegistrationResponse.newBuilder().setSuccess(true)
                .setMessage("User registered successfully").build();
        try {
            RegistrationData registrationData = new RegistrationData(request).getData();
            return response;
        } catch (InvalidAttributeValueException | DomainException e) {
            throw Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException();
        }

    }

}
