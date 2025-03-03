package com.thermal.thermalback.modules.user.work.repository;

import com.thermal.thermalback.modules.user.work.entity.UserWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserWorkRepository extends JpaRepository<UserWork, UUID> {
    void deleteAllByOrderId(UUID userId);
    List<UserWork> findAllByOrderId(UUID userId);
}
