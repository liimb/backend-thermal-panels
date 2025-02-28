package com.thermal.thermalback.modules.material.repository;

import com.thermal.thermalback.modules.material.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MaterialRepository extends JpaRepository<Material, UUID> {
}
