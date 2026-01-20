package com.Mitarbeiterverwaltung.HrManagement.gateways.db;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;
    private String iban;
    private String bic;
    private String accountHolder;
    private LocalDate terminationDate;
    private String terminationReason;
    private String terminationStatus;
    private boolean systemPermissionsRevoked;
    private LocalDateTime systemPermissionsRevokedAt;
    private boolean devicesReturned;
    private LocalDateTime devicesReturnedAt;

    public EmployeeEntity() {
    }

    public EmployeeEntity(int id, String firstName, String lastName, String street, String houseNumber,
            String postalCode, String city, String country, String iban, String bic, String accountHolder,
            LocalDate terminationDate, String terminationReason, String terminationStatus,
            boolean systemPermissionsRevoked, LocalDateTime systemPermissionsRevokedAt,
            boolean devicesReturned, LocalDateTime devicesReturnedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.iban = iban;
        this.bic = bic;
        this.accountHolder = accountHolder;
        this.terminationDate = terminationDate;
        this.terminationReason = terminationReason;
        this.terminationStatus = terminationStatus;
        this.systemPermissionsRevoked = systemPermissionsRevoked;
        this.systemPermissionsRevokedAt = systemPermissionsRevokedAt;
        this.devicesReturned = devicesReturned;
        this.devicesReturnedAt = devicesReturnedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getTerminationReason() {
        return terminationReason;
    }

    public void setTerminationReason(String terminationReason) {
        this.terminationReason = terminationReason;
    }

    public String getTerminationStatus() {
        return terminationStatus;
    }

    public void setTerminationStatus(String terminationStatus) {
        this.terminationStatus = terminationStatus;
    }

    public boolean isSystemPermissionsRevoked() {
        return systemPermissionsRevoked;
    }

    public void setSystemPermissionsRevoked(boolean systemPermissionsRevoked) {
        this.systemPermissionsRevoked = systemPermissionsRevoked;
    }

    public LocalDateTime getSystemPermissionsRevokedAt() {
        return systemPermissionsRevokedAt;
    }

    public void setSystemPermissionsRevokedAt(LocalDateTime systemPermissionsRevokedAt) {
        this.systemPermissionsRevokedAt = systemPermissionsRevokedAt;
    }

    public boolean isDevicesReturned() {
        return devicesReturned;
    }

    public void setDevicesReturned(boolean devicesReturned) {
        this.devicesReturned = devicesReturned;
    }

    public LocalDateTime getDevicesReturnedAt() {
        return devicesReturnedAt;
    }

    public void setDevicesReturnedAt(LocalDateTime devicesReturnedAt) {
        this.devicesReturnedAt = devicesReturnedAt;
    }
}
