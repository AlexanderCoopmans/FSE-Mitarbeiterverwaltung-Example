package com.Mitarbeiterverwaltung.HrManagement.usecases;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.Mitarbeiterverwaltung.HrManagement.domain.Employee;
import com.Mitarbeiterverwaltung.HrManagement.domain.EmployeeNumber;
import com.Mitarbeiterverwaltung.HrManagement.domain.EmploymentContract;
import com.Mitarbeiterverwaltung.HrManagement.domain.TerminationProcessInformation;
import com.Mitarbeiterverwaltung.HrManagement.usecases.primary.HrManagementService;
import com.Mitarbeiterverwaltung.HrManagement.usecases.secondary.EmployeeRepository;
import com.Mitarbeiterverwaltung.HrManagement.usecases.secondary.EmploymentTerminatedEventPublisher;

public class HrManagementServiceImpl implements HrManagementService {

    private final EmployeeRepository employeeRepository;
    private final EmploymentTerminatedEventPublisher employmentTerminatedEventPublisher;

    public HrManagementServiceImpl(EmployeeRepository employeeRepository,
            EmploymentTerminatedEventPublisher employmentTerminatedEventPublisher) {
        this.employeeRepository = employeeRepository;
        this.employmentTerminatedEventPublisher = employmentTerminatedEventPublisher;
    }

    @Override
    public Optional<Employee> createEmployee(Employee employee) {
        if (employee == null) {
            return Optional.empty();
        }
        Employee saved = employeeRepository.save(employee);
        return Optional.ofNullable(saved);
    }

    @Override
    public Optional<Employee> getEmployee(int employeeId) {
        return employeeRepository.findEmployee(EmployeeNumber.of(employeeId));
    }

    @Override
    public List<Employee> getEmployeesActiveAt(LocalDate activeAt) {
        LocalDate targetDate = activeAt != null ? activeAt : LocalDate.now();
        return employeeRepository.findEmployeesActiveAt(targetDate);
    }

    @Override
    public Optional<EmploymentContract> addEmploymentContract(int employeeId, EmploymentContract contract) {
        if (contract == null) {
            return Optional.empty();
        }
        Optional<Employee> existing = employeeRepository.findEmployee(EmployeeNumber.of(employeeId));
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        EmploymentContract savedContract = employeeRepository.saveContract(EmployeeNumber.of(employeeId), contract);
        return Optional.ofNullable(savedContract);
    }

    @Override
    public Optional<Employee> terminateContract(long contractId, LocalDate terminationDate, String reason) {
        Optional<Employee> employeeOpt = employeeRepository.findEmployeeByContractId(contractId);
        if (employeeOpt.isEmpty()) {
            return Optional.empty();
        }
        Employee employee = employeeOpt.get();
        employee.startTerminationProcess(terminationDate, reason);
        EmploymentContract updatedContract = employee.getEmploymentContract().withEndDate(terminationDate);
        employee = employee.withEmploymentContract(updatedContract);
        Employee saved = employeeRepository.save(employee);
        employmentTerminatedEventPublisher.publishEmploymentTermination(saved.getEmployeeNumber().getValue(),
                terminationDate);
        return Optional.of(saved);
    }

    @Override
    public Optional<TerminationProcessInformation> getOffboardingStatus(int employeeId) {
        return employeeRepository.findEmployee(EmployeeNumber.of(employeeId))
                .flatMap(Employee::getTerminationProcessInformation);
    }

    @Override
    public void handleAllDevicesReturned(int employeeId, LocalDate lastReturnDate) {
        Optional<Employee> employeeOpt = employeeRepository.findEmployee(EmployeeNumber.of(employeeId));
        if (employeeOpt.isEmpty()) {
            return;
        }
        Employee employee = employeeOpt.get();
        LocalDateTime timestamp = lastReturnDate != null ? lastReturnDate.atStartOfDay() : LocalDateTime.now();
        try {
            employee.recordDevicesReturned(timestamp);
            employeeRepository.save(employee);
        } catch (IllegalStateException ex) {
            System.out.println("Termination process not started for employee " + employeeId);
        }
    }
}
