package com.thermal.thermalback.modules.auth.repository;

import com.thermal.thermalback.modules.auth.entity.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SmsCodeRepository extends JpaRepository<SmsCode, UUID> {
    Optional<SmsCode> findByPhone(String phone);
    void deleteByPhone(String phone);
}
