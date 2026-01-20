package com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto;

public class EmployeeResponse {
    private int employeeId;
    private String firstName;
    private String lastName;
    private AddressResponse address;
    private BankAccountResponse bankAccount;
    private EmploymentContractResponse contract;

    public EmployeeResponse(int employeeId, String firstName, String lastName, AddressResponse address,
            BankAccountResponse bankAccount, EmploymentContractResponse contract) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.bankAccount = bankAccount;
        this.contract = contract;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public BankAccountResponse getBankAccount() {
        return bankAccount;
    }

    public EmploymentContractResponse getContract() {
        return contract;
    }
}
