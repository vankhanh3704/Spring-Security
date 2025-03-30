package com.devteria.identify_service.Exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error."),
    INVALID_KEY(1001,"Invalid message key."),
    USER_EXISTED(1002, "User already exists."),
    USERNAME_INVALID(1003, "Username must be at least 3 characters."),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters."),
    USER_NOT_EXISTED(1005, "User not exists."),
    UNAUTHENTICATED(1006, "Unauthenticated"),
    ;
    public int code;
    public String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
