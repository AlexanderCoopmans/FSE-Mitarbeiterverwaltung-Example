package com.Mitarbeiterverwaltung.AuthorizationManagement.domain;

import java.util.Objects;

public final class EmployeeReference {

    private final String employeeNumber;

    public EmployeeReference(String employeeNumber) {
        this.employeeNumber = requireText(employeeNumber, "employeeNumber");
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmployeeReference that = (EmployeeReference) o;
        return employeeNumber.equals(that.employeeNumber);
    }

    @Override
    public int hashCode() {
        return employeeNumber.hashCode();
    }

    @Override
    public String toString() {
        return employeeNumber;
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName);
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return trimmed;
    }
}
