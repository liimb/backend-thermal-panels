package com.thermal.thermalback.util.unit;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UnitConverter implements AttributeConverter<Unit, String> {

    @Override
    public String convertToDatabaseColumn(Unit unit) {
        return (unit == null) ? null : unit.getValue();
    }

    @Override
    public Unit convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(Unit.values())
                .filter(unit -> unit.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Некорректное значение единицы измерения: " + value));
    }
}
