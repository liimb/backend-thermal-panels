package com.thermal.thermalback.modules.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "jwt_accounts")
@Accessors(fluent = true)
@EqualsAndHashCode(of = "id")
public class Jwt {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "jwt_id")
    private String jwtId;

    @Column(name = "jwt")
    private String jwt;

    @Column(name = "exp")
    private LocalDateTime exp;

    @Column(name = "nbf")
    private LocalDateTime nbf;

    @Column(name = "iat")
    private LocalDateTime iat;

    @Column(name = "refresh_token")
    private UUID refreshToken;

    @Column(name = "refresh_exp")
    private LocalDateTime refreshExp;
}
