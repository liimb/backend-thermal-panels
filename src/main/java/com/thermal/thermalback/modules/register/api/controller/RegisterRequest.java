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

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("patronymic")
    private String patronymic;

    @JsonProperty("email")
    private String email;

    @JsonProperty("registerUUID")
    private UUID registerUUID;
}
