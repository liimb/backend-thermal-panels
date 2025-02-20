package com.thermal.thermalback.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class JwtConfig {

    @Value("${jwt-expired-time}")
    private Integer jwtExpiredTime;

    @Value("${jwt-refresh-expired-time}")
    private Integer refreshExpiredTime;

}
