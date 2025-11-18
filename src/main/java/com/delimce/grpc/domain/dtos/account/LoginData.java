package com.delimce.grpc.domain.dtos.account;

import javax.management.InvalidAttributeValueException;

import com.delimce.grpc.account.LoginRequest;
import com.delimce.grpc.domain.dtos.commons.ValidationData;
import com.delimce.grpc.domain.exceptions.DomainException;

import lombok.Getter;

@Getter
public class LoginData extends ValidationData {

    private String email;
    private String password;

    public LoginData(LoginRequest request) {
        this.email = request.getEmail();
        this.password = request.getPassword();
    }

    protected boolean isValid() {
        return emailIsValid(email) && passwordIsValid(password);
    }

    public LoginData getData() throws InvalidAttributeValueException, DomainException {
        if (!isValid()) {
            throw new InvalidAttributeValueException("Invalid password, or email.");
        }
        return this;
    }

}
