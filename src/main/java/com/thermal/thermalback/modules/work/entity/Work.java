package com.thermal.thermalback.modules.work.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermal.thermalback.util.unit.Unit;
import com.thermal.thermalback.util.unit.UnitConverter;
import jakarta.persistence.*;
import jakarta.persistence.Convert;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@Entity(name = "works")
@EqualsAndHashCode(of = "id")
public class Work {

    @Id
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "unit")
    //@Enumerated(EnumType.STRING)
    //@Convert(converter = UnitConverter.class)
    @JsonProperty("unit")
    private String unit;

    @Column(name = "price")
    @JsonProperty("price")
    private Float price;
}
