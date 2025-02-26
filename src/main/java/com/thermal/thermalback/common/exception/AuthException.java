package com.thermal.thermalback.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
public class AuthException extends Exception {

    private final String errorCode;
    private final String description;
    private final HttpStatus status;

    public AuthException(AuthErrorCodeEnum errorCodeEnum) {

        super(errorCodeEnum.description());
        this.errorCode = errorCodeEnum.code();
        this.description = errorCodeEnum.description();
        this.status = errorCodeEnum.status();

    }

    public AuthException(String errorCode, String description, HttpStatus status) {

        super(description);
        this.errorCode = errorCode;
        this.description = description;
        this.status = status;

    }
}
