package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.util.Objects;

public final class EmployeeReference {
    private final String employeeNumber;

    private EmployeeReference(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public static EmployeeReference of(String employeeNumber) {
        if (employeeNumber == null || employeeNumber.isBlank()) {
            throw new IllegalArgumentException("Employee reference must not be null or blank");
        }
        return new EmployeeReference(employeeNumber.trim());
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeReference)) {
            return false;
        }
        EmployeeReference that = (EmployeeReference) o;
        return employeeNumber.equals(that.employeeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeNumber);
    }

    @Override
    public String toString() {
        return employeeNumber;
    }
}
