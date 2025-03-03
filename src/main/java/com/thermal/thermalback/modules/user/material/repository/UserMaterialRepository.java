package com.thermal.thermalback.modules.user.material.repository;

import com.thermal.thermalback.modules.user.material.entity.UserMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserMaterialRepository extends JpaRepository<UserMaterial, UUID> {
    void deleteAllByOrderId(UUID userId);
    List<UserMaterial> findAllByOrderId(UUID userId);
}
