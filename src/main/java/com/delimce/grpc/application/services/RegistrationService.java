package com.delimce.grpc.application.services;

import javax.management.InvalidAttributeValueException;

import org.springframework.stereotype.Service;

import com.delimce.grpc.account.RegistrationRequest;
import com.delimce.grpc.account.RegistrationResponse;
import com.delimce.grpc.application.ports.RegistrationServiceInterface;
import com.delimce.grpc.domain.dtos.account.RegistrationData;
import com.delimce.grpc.domain.exceptions.DomainException;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RegistrationService implements RegistrationServiceInterface {

    @SuppressWarnings("finally")
    @Override
    public RegistrationResponse register(RegistrationRequest request) {

        RegistrationResponse response = RegistrationResponse.newBuilder().setSuccess(true)
                .setMessage("User registered successfully").build();

        try {
            RegistrationData registrationData = new RegistrationData(request).getData();
        } catch (InvalidAttributeValueException | DomainException e) {
            log.error("Registration failed: {}", e.getMessage());
            response = RegistrationResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage(e.getMessage())
                    .build();
        } finally {
            return response;
        }
    }

}
