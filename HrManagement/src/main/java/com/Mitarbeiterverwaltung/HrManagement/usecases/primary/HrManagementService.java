package com.Mitarbeiterverwaltung.HrManagement.usecases.primary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.Mitarbeiterverwaltung.HrManagement.domain.Employee;
import com.Mitarbeiterverwaltung.HrManagement.domain.EmploymentContract;
import com.Mitarbeiterverwaltung.HrManagement.domain.TerminationProcessInformation;

public interface HrManagementService {

	Optional<Employee> createEmployee(Employee employee);

	Optional<Employee> getEmployee(int employeeId);

	List<Employee> getEmployeesActiveAt(LocalDate date);

	Optional<EmploymentContract> addEmploymentContract(int employeeId, EmploymentContract contract);

	Optional<Employee> terminateContract(int employeeId, LocalDate terminationDate, String reason);

	Optional<TerminationProcessInformation> getOffboardingStatus(int employeeId);

	void recordSystemPermissionsRevoked(int employeeId, LocalDateTime revokedAt);

	void recordDevicesReturned(int employeeId, LocalDateTime returnedAt);
}

