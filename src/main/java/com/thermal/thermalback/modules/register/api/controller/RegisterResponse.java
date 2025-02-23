package com.thermal.thermalback.modules.register.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermal.thermalback.modules.auth.api.controller.JwtDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class RegisterResponse {

    @Schema(description = "Созданный аккаунт")
    @JsonProperty("accountDto")
    private AccountDto accountDto;

    @Schema(description = "Токены")
    @JsonProperty("jwtDto")
    private JwtDto jwtDto;
}
