package com.delimce.grpc.application.services;

import javax.management.InvalidAttributeValueException;

import org.springframework.stereotype.Service;

import com.delimce.grpc.account.LoginRequest;
import com.delimce.grpc.account.UserAccount;
import com.delimce.grpc.application.ports.LoginServiceInterface;
import com.delimce.grpc.domain.dtos.account.LoginData;
import com.delimce.grpc.domain.exceptions.DomainException;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class LoginService implements LoginServiceInterface {

    @Override
    public UserAccount login(LoginRequest request) {

        try {

            LoginData loginData = new LoginData(request).getData();
            String uid = "uid-" + Math.abs(loginData.getEmail().hashCode());

            UserAccount user = UserAccount.newBuilder()
                    .setUid(uid)
                    .setEmail(loginData.getEmail())
                    .build();

            log.info("User logged in: {}", loginData.getEmail());
            return user;
        } catch (InvalidAttributeValueException | DomainException e) {
            log.error("Login failed: {}", e.getMessage());
            return UserAccount.newBuilder().build();
        }
    }

}
