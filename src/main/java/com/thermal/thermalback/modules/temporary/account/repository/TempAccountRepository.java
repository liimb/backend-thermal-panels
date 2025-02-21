package com.thermal.thermalback.modules.temporary.account.repository;

import com.thermal.thermalback.modules.temporary.account.entity.TempAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TempAccountRepository extends JpaRepository<TempAccount, UUID> {
    Optional<TempAccount> findByPhone(String username);
    Optional<TempAccount> findByRegisterUUID(UUID registerUUID);
}
