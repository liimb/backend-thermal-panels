package com.thermal.thermalback.modules.temporary.account.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
@Entity(name = "temporary_accounts")
@EqualsAndHashCode(of = "id")
public class TempAccount {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "register_uuid")
    private UUID registerUUID;
}
