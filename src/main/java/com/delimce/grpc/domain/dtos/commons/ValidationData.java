package com.delimce.grpc.domain.dtos.commons;

public abstract class ValidationData {

    public final static Boolean VALID_STATUS   = true;
    public final static Boolean INVALID_STATUS = false;

    protected boolean emailIsValid(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    protected boolean passwordIsValid(String password) {
        return password != null && password.length() >= 6;
    }

    protected boolean isNotBlank(String field) {
        return field != null && !field.trim().isEmpty();
    }

    protected boolean isString(Object obj) {
        if (!(obj instanceof String)) return false;
        String s = ((String) obj).trim();
        if (s.isEmpty()) return false;
        // reject strings that are numeric or contain any digit
        return !s.matches(".*\\d.*");
    }

}
