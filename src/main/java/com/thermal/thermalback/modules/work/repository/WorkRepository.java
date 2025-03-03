package com.thermal.thermalback.modules.work.repository;

import com.thermal.thermalback.modules.work.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkRepository extends JpaRepository<Work, UUID> {

}
