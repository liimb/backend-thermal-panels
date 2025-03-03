package com.thermal.thermalback.modules.work.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermal.thermalback.util.unit.Unit;
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
    private Unit unit;

    @JsonProperty("price")
    private Float price;
}
