package com.Mitarbeiterverwaltung.HrManagement.gateways.db;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.Mitarbeiterverwaltung.HrManagement.domain.Address;
import com.Mitarbeiterverwaltung.HrManagement.domain.BankAccount;
import com.Mitarbeiterverwaltung.HrManagement.domain.Employee;
import com.Mitarbeiterverwaltung.HrManagement.domain.EmployeeNumber;
import com.Mitarbeiterverwaltung.HrManagement.domain.EmploymentContract;
import com.Mitarbeiterverwaltung.HrManagement.domain.Name;
import com.Mitarbeiterverwaltung.HrManagement.domain.TerminationProcessInformation;
import com.Mitarbeiterverwaltung.HrManagement.domain.TerminationStatus;
import com.Mitarbeiterverwaltung.HrManagement.usecases.secondary.EmployeeRepository;

@Transactional
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final EmployeeEntityRepository employeeEntityRepository;

    public EmployeeRepositoryImpl(EmployeeEntityRepository employeeEntityRepository) {
        this.employeeEntityRepository = employeeEntityRepository;
    }

    @Override
    public Optional<Employee> findById(int employeeId) {
        Optional<EmployeeEntity> entityOpt = employeeEntityRepository.findById(employeeId);
        if (entityOpt.isEmpty()) {
            return Optional.empty();
        }
        return entityOpt.map(this::toDomain);
    }

    @Override
    public Employee save(Employee employee) {
        EmployeeEntity entity = toEntity(employee);
        employeeEntityRepository.save(entity);
        return toDomain(entity);
    }

    @Override
        public List<Employee> findEmployeesActiveAt(LocalDate date) {
        return employeeEntityRepository.findAll().stream()
            .map(this::toDomain)
            .filter(Objects::nonNull)
            .filter(emp -> emp.isActiveOn(date))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<EmploymentContract> addContract(int employeeId, EmploymentContract contract) {
        return employeeEntityRepository.findById(employeeId)
                .map(entity -> {
                    entity.setJobTitle(contract.getJobTitle());
                    entity.setResponsibilities(contract.getResponsibilities());
                    entity.setAnnualSalary(contract.getAnnualSalary().getAmount());
                    entity.setCurrency(contract.getAnnualSalary().getCurrency());
                    entity.setContractStartDate(contract.getStartDate());
                    entity.setContractEndDate(contract.getEndDate());
                    employeeEntityRepository.save(entity);
                    return contract;
                });
    }

    private Employee toDomain(EmployeeEntity entity) {
        if (entity == null) {
            return null;
        }
        EmploymentContract contract = EmploymentContract.of(entity.getJobTitle(), entity.getResponsibilities(),
                com.Mitarbeiterverwaltung.HrManagement.domain.Money.of(entity.getAnnualSalary(), entity.getCurrency()),
                entity.getContractStartDate(), entity.getContractEndDate());
        TerminationProcessInformation termination = null;
        if (entity.getTerminationDate() != null && entity.getTerminationReason() != null) {
            TerminationStatus status = entity.getTerminationStatus() != null
                    ? TerminationStatus.valueOf(entity.getTerminationStatus())
                    : TerminationStatus.IN_PROGRESS;
            termination = TerminationProcessInformation.restore(entity.getTerminationDate(),
                    entity.getTerminationReason(),
                    status,
                    entity.getSystemPermissionsRevokedAt(),
                    entity.getDevicesReturnedAt());
        }
        return Employee.restore(
                EmployeeNumber.of(entity.getId()),
                Name.of(entity.getFirstName(), entity.getLastName()),
                Address.of(entity.getStreet(), entity.getHouseNumber(), entity.getPostalCode(),
                        entity.getCity(), entity.getCountry()),
                BankAccount.of(entity.getIban(), entity.getBic(), entity.getAccountHolder()),
                contract,
                termination);
    }

    private EmployeeEntity toEntity(Employee employee) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(employee.getEmployeeNumber().getValue());
        entity.setFirstName(employee.getName().getFirstName());
        entity.setLastName(employee.getName().getLastName());
        entity.setStreet(employee.getAddress().getStreet());
        entity.setHouseNumber(employee.getAddress().getHouseNumber());
        entity.setPostalCode(employee.getAddress().getPostalCode());
        entity.setCity(employee.getAddress().getCity());
        entity.setCountry(employee.getAddress().getCountry());
        entity.setIban(employee.getBankAccount().getIban());
        entity.setBic(employee.getBankAccount().getBic());
        entity.setAccountHolder(employee.getBankAccount().getAccountHolder());

        employee.getTerminationProcessInformation().ifPresentOrElse(info -> {
            entity.setTerminationDate(info.getTerminationDate());
            entity.setTerminationReason(info.getTerminationReason());
            entity.setTerminationStatus(info.getStatus().name());
            entity.setSystemPermissionsRevokedAt(info.getLastSystemPermissionRevokedAt());
            entity.setDevicesReturnedAt(info.getLastDevicesReturnedAt());
        }, () -> {
            entity.setTerminationDate(null);
            entity.setTerminationReason(null);
            entity.setTerminationStatus(null);
            entity.setSystemPermissionsRevokedAt(null);
            entity.setDevicesReturnedAt(null);
        });
        return entity;
    }
}
