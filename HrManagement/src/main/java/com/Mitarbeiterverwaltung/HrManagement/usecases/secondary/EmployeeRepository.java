package com.Mitarbeiterverwaltung.HrManagement.usecases.secondary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.Mitarbeiterverwaltung.HrManagement.domain.Employee;
import com.Mitarbeiterverwaltung.HrManagement.domain.EmployeeNumber;
import com.Mitarbeiterverwaltung.HrManagement.domain.EmploymentContract;

public interface EmployeeRepository {
    Employee save(Employee employee);

    Optional<Employee> findEmployee(EmployeeNumber employeeNumber);

    List<Employee> findEmployeesActiveAt(LocalDate date);

    EmploymentContract saveContract(EmployeeNumber employeeNumber, EmploymentContract contract);
}
