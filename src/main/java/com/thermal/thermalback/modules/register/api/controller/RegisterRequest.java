package com.thermal.thermalback.modules.register.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class RegisterRequest {

    @JsonProperty("accountDto")
    private AccountDto accountDto;

    @JsonProperty("registerUUID")
    private UUID registerUUID;
}
