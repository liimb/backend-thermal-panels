package com.thermal.thermalback.modules.user.material;

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
public class UserMaterialDto {

    @JsonProperty("materialId")
    private UUID materialId;

    @JsonProperty("count")
    private Float count;
}
