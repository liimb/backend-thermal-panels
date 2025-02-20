package com.thermal.thermalback.common.config;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class KeyConfig {

    @Value("${cert.path.publicKey}")
    private String pathToPublicKey;

    @Value("${cert.path.privateKey}")
    private String pathToPrivateKey;

}
