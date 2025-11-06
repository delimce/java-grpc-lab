package com.delimce.grpc.domain.dtos.account;

import com.delimce.grpc.account.RegistrationRequest;
import com.delimce.grpc.domain.dtos.commons.ValidationData;
import com.delimce.grpc.domain.exceptions.DomainException;

import lombok.Getter;

@Getter
public class RegistrationData extends ValidationData {

    private String name;
    private String lastName;
    private String email;

    public RegistrationData(RegistrationRequest request) {
        this.name = request.getName();
        this.lastName = request.getLastName();
        this.email = request.getEmail();
    }

    protected boolean isValid() {
        return (isNotBlank(name) && isString(name)) &&
                (isNotBlank(lastName) && isString(lastName)) &&
                emailIsValid(email);
    }

    public RegistrationData getData() throws DomainException {
        if (!isValid()) {
            throw new DomainException("Invalid registration name, surname or email", null);
        }
        return this;
    }

}
