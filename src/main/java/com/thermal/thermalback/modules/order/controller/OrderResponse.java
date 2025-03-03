package com.thermal.thermalback.modules.order.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermal.thermalback.modules.user.material.UserMaterialDto;
import com.thermal.thermalback.modules.user.work.UserWorkDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class OrderResponse {

    @JsonProperty("orderId")
    private UUID orderId;

    @JsonProperty("userId")
    private UUID userId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("userMaterials")
    private List<UserMaterialDto> userMaterials;

    @JsonProperty("userWorks")
    private List<UserWorkDto> userWorks;
}
