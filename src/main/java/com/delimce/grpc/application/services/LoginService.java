package com.delimce.grpc.application.services;

import com.delimce.grpc.account.LoginRequest;
import com.delimce.grpc.account.UserAccount;
import com.delimce.grpc.application.ports.LoginServiceInterface;
import com.delimce.grpc.domain.dtos.account.LoginData;
import com.delimce.grpc.domain.exceptions.DomainException;
import io.grpc.Status;
import javax.management.InvalidAttributeValueException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginServiceInterface {

    @Override
    public UserAccount login(LoginRequest request) {
        try {
            LoginData loginData = new LoginData(request).getData();

            // replace uid later with real user id from database
            String uid = "uid-" + Math.abs(loginData.getEmail().hashCode());

            UserAccount user = UserAccount.newBuilder()
                .setUid(uid)
                .setEmail(loginData.getEmail())
                .build();

            return user;
        } catch (InvalidAttributeValueException | DomainException e) {
            throw Status.INVALID_ARGUMENT.withDescription(
                e.getMessage()
            ).asRuntimeException();
        }
    }
}
