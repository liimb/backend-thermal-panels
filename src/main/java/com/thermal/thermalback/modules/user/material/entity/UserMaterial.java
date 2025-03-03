package com.thermal.thermalback.modules.user.material.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Entity(name = "user_materials")
@EqualsAndHashCode(of = "id")
public class UserMaterial {

    @Id
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;

    @Column(name = "material_id")
    @JsonProperty("materialId")
    private UUID materialId;

    @Column(name = "order_id")
    @JsonProperty("orderId")
    private UUID orderId;

    @Column(name = "count")
    @JsonProperty("count")
    private Float count;
}
