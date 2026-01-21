package com.Mitarbeiterverwaltung.HrManagement.usecases;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.Mitarbeiterverwaltung.HrManagement.domain.Employee;
import com.Mitarbeiterverwaltung.HrManagement.domain.TerminationProcessInformation;
import com.Mitarbeiterverwaltung.HrManagement.usecases.primary.HrManagementService;
import com.Mitarbeiterverwaltung.HrManagement.usecases.secondary.EmployeeRepository;
import com.Mitarbeiterverwaltung.HrManagement.usecases.secondary.EmploymentTerminatedEventPublisher;

@Transactional
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
        int employeeId = employee.getEmployeeNumber().getValue();
        if (employeeRepository.findById(employeeId).isPresent()) {
            return Optional.empty();
        }
        Employee saved = employeeRepository.save(employee);
        return Optional.ofNullable(saved);
    }

    @Override
    public Optional<Employee> getEmployee(int employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public List<Employee> getEmployeesActiveAt(LocalDate date) {
        LocalDate targetDate = date != null ? date : LocalDate.now();
        return employeeRepository.findEmployeesActiveAt(targetDate);
    }


    @Override
    public Optional<Employee> terminateContract(int employeeId, LocalDate terminationDate, String reason) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return Optional.empty();
        }
        Employee employee = employeeOpt.get();
        employee.startTerminationProcess(terminationDate, reason);
        Employee saved = employeeRepository.save(employee);
        employmentTerminatedEventPublisher.publishTermination(employeeId, terminationDate);
        return Optional.of(saved);
    }

    @Override
    public Optional<TerminationProcessInformation> getOffboardingStatus(int employeeId) {
        return employeeRepository.findById(employeeId).flatMap(Employee::getTerminationProcessInformation);
    }

    @Override
    public void recordSystemPermissionsRevoked(int employeeId, LocalDateTime revokedAt) {
        employeeRepository.findById(employeeId).ifPresent(employee -> {
            if (employee.getTerminationProcessInformation().isPresent()) {
                employee.recordSystemPermissionsRevoked(revokedAt);
                employeeRepository.save(employee);
            }
        });
    }

    @Override
    public void recordDevicesReturned(int employeeId, LocalDateTime returnedAt) {
        employeeRepository.findById(employeeId).ifPresent(employee -> {
            if (employee.getTerminationProcessInformation().isPresent()) {
                employee.recordDevicesReturned(returnedAt);
                employeeRepository.save(employee);
            }
        });
    }
}
