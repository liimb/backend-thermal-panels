package com.thermal.thermalback.modules.order.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermal.thermalback.util.OrderStatus;
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
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;

    @Column(name = "user_id")
    @JsonProperty("userId")
    private UUID userId;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "status")
    @JsonProperty("status")
    private String status;
}
