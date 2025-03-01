package com.thermal.thermalback.modules.works.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermal.thermalback.util.Unit;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class WorkDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("unit")
    @Enumerated(EnumType.STRING)
    private Unit unit;

    @JsonProperty("price")
    private Float price;
}
