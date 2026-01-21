package com.Mitarbeiterverwaltung.HrManagement.usecases.secondary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.Mitarbeiterverwaltung.HrManagement.domain.Employee;
import com.Mitarbeiterverwaltung.HrManagement.domain.EmploymentContract;

public interface EmployeeRepository {
    Optional<Employee> findById(int employeeId);

    Employee save(Employee employee);

    List<Employee> findEmployeesActiveAt(LocalDate date);

    Optional<EmploymentContract> addContract(int employeeId, EmploymentContract contract);
}
