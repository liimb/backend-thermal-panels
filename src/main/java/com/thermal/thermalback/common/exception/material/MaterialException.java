package com.thermal.thermalback.common.exception.material;

import com.thermal.thermalback.common.exception.auth.AuthErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
public class MaterialException extends Exception {

    private final String errorCode;
    private final String description;
    private final HttpStatus status;

    public MaterialException(MaterialErrorCodeEnum errorCodeEnum) {

        super(errorCodeEnum.description());
        this.errorCode = errorCodeEnum.code();
        this.description = errorCodeEnum.description();
        this.status = errorCodeEnum.status();

    }

    public MaterialException(String errorCode, String description, HttpStatus status) {

        super(description);
        this.errorCode = errorCode;
        this.description = description;
        this.status = status;

    }
}
