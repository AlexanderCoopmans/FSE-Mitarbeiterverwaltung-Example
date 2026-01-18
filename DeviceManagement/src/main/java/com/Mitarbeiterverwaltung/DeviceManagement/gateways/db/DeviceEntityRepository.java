package com.Mitarbeiterverwaltung.DeviceManagement.gateways.db;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceEntityRepository extends JpaRepository<DeviceEntity, Integer> {
    @Query("SELECT d FROM DeviceEntity d WHERE d.assignmentEnd >= :start AND d.assignmentEnd <= :end")
    List<DeviceEntity> findByDateRange(LocalDate start, LocalDate end);

    @Query("SELECT d FROM DeviceEntity d WHERE d.employeeNumber = :employeeNumber AND d.assignmentStart IS NOT NULL AND (d.assignmentEnd IS NULL OR d.assignmentEnd >= :today)")
    List<DeviceEntity> findActiveAssignmentsByEmployee(String employeeNumber, LocalDate today);

    DeviceEntity findByAssignmentId(String assignmentId);
}
