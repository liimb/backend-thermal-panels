package com.thermal.thermalback.modules.auth.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@AllArgsConstructor
@Accessors(fluent = true)
public class RefreshJwtRequest {

    @JsonProperty("jwt")
    private String jwt;

    @JsonProperty("refreshToken")
    private UUID refreshToken;
}
