package com.thermal.thermalback.common.exception.material;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public enum MaterialErrorCodeEnum {
    MATERIAL_NOT_FOUND("000", "Материал не найден", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String description;
    private final HttpStatus status;
}
