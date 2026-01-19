package com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.db;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionEntityRepository extends JpaRepository<PermissionEntity, String> {

    @Query("SELECT p FROM PermissionEntity p WHERE p.employeeNumber = :employeeNumber ORDER BY p.validFrom DESC")
    List<PermissionEntity> findByEmployeeNumber(int employeeNumber);

    @Query("SELECT p FROM PermissionEntity p WHERE p.employeeNumber = :employeeNumber AND p.revoked = false AND p.validFrom <= :onDate AND p.validUntil >= :onDate")
    List<PermissionEntity> findActiveByEmployeeNumber(int employeeNumber, LocalDate onDate);
}
