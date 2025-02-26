package com.thermal.thermalback.modules.auth.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class JwtDto {

    @Schema(description = "Token пользователя")
    @JsonProperty("jwt")
    private String jwt;

    @Schema(description = "Refresh token пользователя")
    @JsonProperty("refreshToken")
    private UUID refreshToken;

    @Schema(description = "Максимальный период актуальности токена в секундах")
    @JsonProperty("maxAge")
    private Integer maxAge;
}
