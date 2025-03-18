package com.thermal.thermalback.common.exception.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public enum GlobalErrorCodeEnum {

    INTERNAL_SERVER_ERROR("000", "Внутренняя ошибка сервера", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String description;
    private final HttpStatus status;
}
