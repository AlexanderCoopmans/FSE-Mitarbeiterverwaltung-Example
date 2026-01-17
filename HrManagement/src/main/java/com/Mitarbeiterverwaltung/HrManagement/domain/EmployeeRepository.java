package com.Mitarbeiterverwaltung.HrManagement.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findByEmployeeNumber(EmployeeNumber employeeNumber);

    List<Employee> findActiveOn(LocalDate date);

    List<Employee> findAll();

    void save(Employee employee);
}
