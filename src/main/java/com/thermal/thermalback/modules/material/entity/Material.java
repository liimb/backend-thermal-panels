package com.thermal.thermalback.modules.material.entity;

import com.thermal.thermalback.modules.material.util.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@Entity(name = "materials")
@EqualsAndHashCode(of = "id")
public class Material {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Column(name = "price")
    private Float price;
}
