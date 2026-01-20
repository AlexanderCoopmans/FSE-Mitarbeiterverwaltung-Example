package com.Mitarbeiterverwaltung.HrManagement.gateways.db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.Mitarbeiterverwaltung.HrManagement.domain.Address;
import com.Mitarbeiterverwaltung.HrManagement.domain.BankAccount;
import com.Mitarbeiterverwaltung.HrManagement.domain.Employee;
import com.Mitarbeiterverwaltung.HrManagement.domain.EmployeeNumber;
import com.Mitarbeiterverwaltung.HrManagement.domain.EmploymentContract;
import com.Mitarbeiterverwaltung.HrManagement.domain.Money;
import com.Mitarbeiterverwaltung.HrManagement.domain.Name;
import com.Mitarbeiterverwaltung.HrManagement.domain.TerminationProcessInformation;
import com.Mitarbeiterverwaltung.HrManagement.domain.TerminationStatus;
import com.Mitarbeiterverwaltung.HrManagement.usecases.secondary.EmployeeRepository;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final EmployeeEntityRepository employeeEntityRepository;
    private final EmploymentContractEntityRepository employmentContractEntityRepository;

    public EmployeeRepositoryImpl(EmployeeEntityRepository employeeEntityRepository,
            EmploymentContractEntityRepository employmentContractEntityRepository) {
        this.employeeEntityRepository = employeeEntityRepository;
        this.employmentContractEntityRepository = employmentContractEntityRepository;
    }

    @Override
    public Employee save(Employee employee) {
        EmployeeEntity employeeEntity = toEntity(employee);
        employeeEntityRepository.save(employeeEntity);
        EmploymentContract persistedContract = upsertContract(employee.getEmployeeNumber().getValue(),
                employee.getEmploymentContract());
        return employee.withEmploymentContract(persistedContract);
    }

    @Override
    public Optional<Employee> findEmployee(EmployeeNumber employeeNumber) {
        Optional<EmployeeEntity> employeeEntity = employeeEntityRepository.findById(employeeNumber.getValue());
        if (employeeEntity.isEmpty()) {
            return Optional.empty();
        }
        List<EmploymentContractEntity> contracts = employmentContractEntityRepository
                .findByEmployeeIdOrderByStartDateDesc(employeeNumber.getValue());
        EmploymentContractEntity currentContractEntity = selectCurrentContract(contracts, LocalDate.now());
        if (currentContractEntity == null) {
            return Optional.empty();
        }
        EmploymentContract contract = toDomainContract(currentContractEntity);
        Employee employee = toDomainEmployee(employeeEntity.get(), contract);
        return Optional.ofNullable(employee);
    }

    @Override
    public List<Employee> findEmployeesActiveAt(LocalDate date) {
        List<EmploymentContractEntity> activeContracts = employmentContractEntityRepository.findActiveOn(date);
        Set<Integer> processedEmployees = new HashSet<>();
        List<Employee> employees = new ArrayList<>();
        for (EmploymentContractEntity contractEntity : activeContracts) {
            if (processedEmployees.contains(contractEntity.getEmployeeId())) {
                continue;
            }
            Optional<EmployeeEntity> employeeEntity = employeeEntityRepository.findById(contractEntity.getEmployeeId());
            if (employeeEntity.isEmpty()) {
                continue;
            }
            EmploymentContract contract = toDomainContract(contractEntity);
            Employee employee = toDomainEmployee(employeeEntity.get(), contract);
            if (employee != null) {
                employees.add(employee);
                processedEmployees.add(contractEntity.getEmployeeId());
            }
        }
        return employees;
    }

    @Override
    public EmploymentContract saveContract(EmployeeNumber employeeNumber, EmploymentContract contract) {
        List<EmploymentContractEntity> existingContracts = employmentContractEntityRepository
                .findByEmployeeIdOrderByStartDateDesc(employeeNumber.getValue());
        EmploymentContractEntity activeContract = selectCurrentContract(existingContracts, contract.getStartDate());
        if (activeContract != null) {
            LocalDate newEndDate = contract.getStartDate().minusDays(1);
            if (activeContract.getEndDate() == null || activeContract.getEndDate().isAfter(newEndDate)) {
                activeContract.setEndDate(newEndDate);
            }
            activeContract.setStatus(resolveStatus(activeContract.getEndDate()));
            employmentContractEntityRepository.save(activeContract);
        }
        EmploymentContractEntity entity = toEntity(employeeNumber.getValue(), contract);
        EmploymentContractEntity saved = employmentContractEntityRepository.save(entity);
        return toDomainContract(saved);
    }

    private EmploymentContract upsertContract(int employeeId, EmploymentContract contract) {
        if (contract == null) {
            return null;
        }
        EmploymentContractEntity entity = toEntity(employeeId, contract);
        entity.setStatus(resolveStatus(contract.getEndDate()));
        EmploymentContractEntity saved = employmentContractEntityRepository.save(entity);
        return toDomainContract(saved);
    }

    private String resolveStatus(LocalDate endDate) {
        if (endDate == null) {
            return "ACTIVE";
        }
        return endDate.isBefore(LocalDate.now()) ? "TERMINATED" : "ACTIVE";
    }

    private EmploymentContractEntity selectCurrentContract(List<EmploymentContractEntity> contracts, LocalDate date) {
        if (contracts == null || contracts.isEmpty()) {
            return null;
        }
        for (EmploymentContractEntity entity : contracts) {
            boolean startsBefore = entity.getStartDate() == null || !entity.getStartDate().isAfter(date);
            boolean endsAfter = entity.getEndDate() == null || !entity.getEndDate().isBefore(date);
            if (startsBefore && endsAfter) {
                return entity;
            }
        }
        return contracts.get(0);
    }

    private EmploymentContractEntity toEntity(int employeeId, EmploymentContract contract) {
        EmploymentContractEntity entity = new EmploymentContractEntity();
        entity.setEmployeeId(employeeId);
        Long existingId = findExistingContractId(employeeId, contract.getStartDate());
        if (existingId != null) {
            entity.setId(existingId);
        }
        entity.setJobTitle(contract.getJobTitle());
        entity.setResponsibilities(contract.getResponsibilities());
        entity.setAnnualSalary(contract.getAnnualSalary().getAmount());
        entity.setCurrency(contract.getAnnualSalary().getCurrency());
        entity.setStartDate(contract.getStartDate());
        entity.setEndDate(contract.getEndDate());
        entity.setStatus(resolveStatus(contract.getEndDate()));
        return entity;
    }

    private EmploymentContract toDomainContract(EmploymentContractEntity entity) {
        if (entity == null) {
            return null;
        }
        Money salary = Money.of(entity.getAnnualSalary(), entity.getCurrency());
        return EmploymentContract.of(entity.getJobTitle(), entity.getResponsibilities(), salary,
                entity.getStartDate(), entity.getEndDate());
    }

    private Long findExistingContractId(int employeeId, LocalDate startDate) {
        if (startDate == null) {
            return null;
        }
        return employmentContractEntityRepository.findByEmployeeIdOrderByStartDateDesc(employeeId).stream()
                .filter(entity -> startDate.equals(entity.getStartDate()))
                .map(EmploymentContractEntity::getId)
                .findFirst()
                .orElse(null);
    }

    private Employee toDomainEmployee(EmployeeEntity entity, EmploymentContract contract) {
        if (contract == null) {
            return null;
        }
        Name name = Name.of(entity.getFirstName(), entity.getLastName());
        Address address = Address.of(entity.getStreet(), entity.getHouseNumber(), entity.getPostalCode(),
                entity.getCity(), entity.getCountry());
        BankAccount bankAccount = BankAccount.of(entity.getIban(), entity.getBic(), entity.getAccountHolder());
        TerminationProcessInformation termination = null;
        if (entity.getTerminationDate() != null && entity.getTerminationReason() != null) {
            TerminationStatus status = entity.getTerminationStatus() != null
                    ? TerminationStatus.valueOf(entity.getTerminationStatus())
                    : TerminationStatus.IN_PROGRESS;
            termination = TerminationProcessInformation.restore(entity.getTerminationDate(),
                    entity.getTerminationReason(), status, entity.getSystemPermissionsRevokedAt(),
                    entity.getDevicesReturnedAt());
        }
        return Employee.restore(EmployeeNumber.of(entity.getId()), name, address, bankAccount, contract, termination);
    }

    private EmployeeEntity toEntity(Employee employee) {
        TerminationProcessInformation termination = employee.getTerminationProcessInformation().orElse(null);
        return new EmployeeEntity(
                employee.getEmployeeNumber().getValue(),
                employee.getName().getFirstName(),
                employee.getName().getLastName(),
                employee.getAddress().getStreet(),
                employee.getAddress().getHouseNumber(),
                employee.getAddress().getPostalCode(),
                employee.getAddress().getCity(),
                employee.getAddress().getCountry(),
                employee.getBankAccount().getIban(),
                employee.getBankAccount().getBic(),
                employee.getBankAccount().getAccountHolder(),
                termination != null ? termination.getTerminationDate() : null,
                termination != null ? termination.getTerminationReason() : null,
                termination != null ? termination.getStatus().name() : null,
                termination != null ? termination.getLastSystemPermissionRevokedAt() : null,
                termination != null ? termination.getLastDevicesReturnedAt() : null);
    }
}
