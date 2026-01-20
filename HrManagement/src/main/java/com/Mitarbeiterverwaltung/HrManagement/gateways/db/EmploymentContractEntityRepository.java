package com.Mitarbeiterverwaltung.HrManagement.gateways.db;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmploymentContractEntityRepository extends JpaRepository<EmploymentContractEntity, Long> {

    @Query("SELECT c FROM EmploymentContractEntity c WHERE c.employeeId = :employeeId ORDER BY c.startDate DESC")
    List<EmploymentContractEntity> findByEmployeeIdOrderByStartDateDesc(int employeeId);

    @Query("SELECT c FROM EmploymentContractEntity c WHERE c.startDate <= :date AND (c.endDate IS NULL OR c.endDate >= :date)")
    List<EmploymentContractEntity> findActiveOn(LocalDate date);

    @Query("SELECT c FROM EmploymentContractEntity c WHERE c.employeeId = :employeeId AND c.startDate <= :date ORDER BY c.startDate DESC")
    List<EmploymentContractEntity> findContractsUpToDate(int employeeId, LocalDate date);
}
