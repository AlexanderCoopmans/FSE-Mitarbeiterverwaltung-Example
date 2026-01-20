package com.Mitarbeiterverwaltung.HrManagement.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class Employee {
    private final EmployeeNumber employeeNumber;
    private Name name;
    private Address address;
    private BankAccount bankAccount;
    private EmploymentContract employmentContract;
    private TerminationProcessInformation terminationProcessInformation;

    private Employee(EmployeeNumber employeeNumber,
                     Name name,
                     Address address,
                     BankAccount bankAccount,
                     EmploymentContract employmentContract,
                     TerminationProcessInformation terminationProcessInformation) {
        this.employeeNumber = requireNonNull(employeeNumber, "employeeNumber");
        this.name = requireNonNull(name, "name");
        this.address = requireNonNull(address, "address");
        this.bankAccount = requireNonNull(bankAccount, "bankAccount");
        this.employmentContract = requireNonNull(employmentContract, "employmentContract");
        this.terminationProcessInformation = terminationProcessInformation;
    }

    public static Employee hire(EmployeeNumber employeeNumber,
                                Name name,
                                Address address,
                                BankAccount bankAccount,
                                EmploymentContract employmentContract) {
        return new Employee(employeeNumber, name, address, bankAccount, employmentContract, null);
    }

    public static Employee restore(EmployeeNumber employeeNumber,
                                   Name name,
                                   Address address,
                                   BankAccount bankAccount,
                                   EmploymentContract employmentContract,
                                   TerminationProcessInformation terminationProcessInformation) {
        return new Employee(employeeNumber, name, address, bankAccount, employmentContract, terminationProcessInformation);
    }

    public void changeAddress(Address newAddress) {
        this.address = requireNonNull(newAddress, "address");
    }

    public void changeBankAccount(BankAccount newBankAccount) {
        this.bankAccount = requireNonNull(newBankAccount, "bankAccount");
    }

    public void startTerminationProcess(LocalDate terminationDate, String reason) {
        if (terminationProcessInformation != null) {
            throw new IllegalStateException("Termination process already started");
        }
        if (terminationDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("terminationDate must not be in the past");
        }
        TerminationProcessInformation info = TerminationProcessInformation.start(
                requireNonNull(terminationDate, "terminationDate"),
                requireNonBlank(reason, "reason"));
        this.employmentContract = this.employmentContract.withEndDate(terminationDate);
        this.terminationProcessInformation = info;
    }

    public void recordSystemPermissionsRevoked(LocalDateTime revokedAt) {
        ensureTerminationProcessExists();
        this.terminationProcessInformation = this.terminationProcessInformation.markSystemPermissionsRevoked(revokedAt);
    }

    public void recordDevicesReturned(LocalDateTime returnedAt) {
        ensureTerminationProcessExists();
        this.terminationProcessInformation = this.terminationProcessInformation.markDevicesReturned(returnedAt);
    }

    public boolean isActiveOn(LocalDate date) {
        return employmentContract.isActiveOn(date);
    }

    public EmployeeNumber getEmployeeNumber() {
        return employeeNumber;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public EmploymentContract getEmploymentContract() {
        return employmentContract;
    }

    public Optional<TerminationProcessInformation> getTerminationProcessInformation() {
        return Optional.ofNullable(terminationProcessInformation);
    }

    public Employee withEmploymentContract(EmploymentContract newContract) {
        return new Employee(employeeNumber, name, address, bankAccount, requireNonNull(newContract, "employmentContract"), terminationProcessInformation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        Employee employee = (Employee) o;
        return employeeNumber.equals(employee.employeeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeNumber);
    }

    @Override
    public String toString() {
        return "Employee " + employeeNumber + " - " + name;
    }

    private void ensureTerminationProcessExists() {
        if (terminationProcessInformation == null) {
            throw new IllegalStateException("Termination process not started");
        }
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " must not be null");
        }
        return value;
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
