package com.thermal.thermalback.common.exception.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public enum OrderErrorCodeEnum {
    ORDER_ERROR("000", "Ошибка создания заказа", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND("001", "Заказ не найден", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String description;
    private final HttpStatus status;
}
