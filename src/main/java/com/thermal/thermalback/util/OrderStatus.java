package com.thermal.thermalback.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    CONFIRMED("Подтвержден"),
    REJECTED("Отклонен"),
    ON_CONSIDERATION("На рассмотрении"),
    COMPLETED("Завершен");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static OrderStatus fromValue(String value) {
        for (OrderStatus orderStatus : values()) {
            if (orderStatus.value.equalsIgnoreCase(value)) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("Некорректное значение: " + value);
    }
}
