package com.delimce.grpc.application.ports;

import com.delimce.grpc.account.RegistrationRequest;
import com.delimce.grpc.account.RegistrationResponse;

public interface RegistrationServiceInterface {

    public RegistrationResponse register(RegistrationRequest request);

}
