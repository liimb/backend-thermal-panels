package com.thermal.thermalback.modules.auth.repository;

import com.thermal.thermalback.modules.auth.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JwtRepository extends JpaRepository<Jwt, UUID> { }
