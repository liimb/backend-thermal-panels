package com.thermal.thermalback.util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Unit {
    M_P("м.п."),
    SQ_M("кв.м.");

    private final String value;

    Unit(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
