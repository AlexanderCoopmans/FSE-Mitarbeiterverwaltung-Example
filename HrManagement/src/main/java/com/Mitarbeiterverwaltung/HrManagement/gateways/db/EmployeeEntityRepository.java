package com.Mitarbeiterverwaltung.HrManagement.gateways.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeEntityRepository extends JpaRepository<EmployeeEntity, Integer> {
}
