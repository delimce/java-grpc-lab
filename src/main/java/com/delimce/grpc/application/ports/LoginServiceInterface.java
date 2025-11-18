package com.delimce.grpc.application.ports;

import com.delimce.grpc.account.LoginRequest;
import com.delimce.grpc.account.UserAccount;

public interface LoginServiceInterface {

    public UserAccount login(LoginRequest request);
}
