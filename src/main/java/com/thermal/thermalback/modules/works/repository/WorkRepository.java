package com.thermal.thermalback.modules.works.repository;

import com.thermal.thermalback.modules.works.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkRepository extends JpaRepository<Work, UUID> { }
