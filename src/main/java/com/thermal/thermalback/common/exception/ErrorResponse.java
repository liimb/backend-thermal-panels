package com.thermal.thermalback.common.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class ErrorResponse {

    @JsonProperty("description")
    private String description;

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("status")
    private HttpStatus status;
}
