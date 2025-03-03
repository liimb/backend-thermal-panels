package com.thermal.thermalback.util.unit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Unit {
    M_P("м.п."),
    SQ_M("кв.м."),
    PC("шт.");

    private final String value;

    Unit(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Unit fromValue(String value) {
        for (Unit unit : values()) {
            if (unit.value.equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Некорректное значение: " + value);
    }
}
