package com.thermal.thermalback.common.exception.global;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
public class GlobalException extends Exception {

    private final String errorCode;
    private final String description;
    private final HttpStatus status;

    public GlobalException(GlobalErrorCodeEnum errorCodeEnum) {

        super(errorCodeEnum.description());
        this.errorCode = errorCodeEnum.code();
        this.description = errorCodeEnum.description();
        this.status = errorCodeEnum.status();

    }
}
