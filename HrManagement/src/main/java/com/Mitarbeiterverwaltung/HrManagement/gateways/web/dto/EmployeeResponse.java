package com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto;

public class EmployeeResponse {
    private int employeeId;
    private String firstName;
    private String lastName;
    private AddressResponse address;
    private BankAccountResponse bankAccount;
    private EmploymentContractResponse employmentContract;

    public EmployeeResponse() {
    }

    public EmployeeResponse(int employeeId, String firstName, String lastName, AddressResponse address,
            BankAccountResponse bankAccount, EmploymentContractResponse employmentContract) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.bankAccount = bankAccount;
        this.employmentContract = employmentContract;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }

    public BankAccountResponse getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountResponse bankAccount) {
        this.bankAccount = bankAccount;
    }

    public EmploymentContractResponse getEmploymentContract() {
        return employmentContract;
    }

    public void setEmploymentContract(EmploymentContractResponse employmentContract) {
        this.employmentContract = employmentContract;
    }
}
