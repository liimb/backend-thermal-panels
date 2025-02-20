package com.thermal.thermalback.modules.auth.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class AuthRequestBySms {

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("code")
    private String code;

}
