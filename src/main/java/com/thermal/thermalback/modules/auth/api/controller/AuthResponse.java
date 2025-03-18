package com.thermal.thermalback.modules.auth.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermal.thermalback.modules.register.api.controller.AccountDto;
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
public class AuthResponse {

    @Schema(description = "Зарегистрирован ли пользователь")
    @JsonProperty("userIsRegister")
    private boolean userIsRegister;

    @Schema(description = "UUID для продолжения регистрации")
    @JsonProperty("registerUUID")
    private UUID registerUUID;

    @Schema(description = "Токены, если аутентифицирован")
    @JsonProperty("jwtDto")
    private JwtDto jwtDto;

    @Schema(description = "Аккаунт, если зарегистрирован")
    @JsonProperty("accountDto")
    private AccountDto accountDto;
}
