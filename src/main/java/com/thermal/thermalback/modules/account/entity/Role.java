package com.thermal.thermalback.modules.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public enum Role {
    USER,
    ADMIN
}
