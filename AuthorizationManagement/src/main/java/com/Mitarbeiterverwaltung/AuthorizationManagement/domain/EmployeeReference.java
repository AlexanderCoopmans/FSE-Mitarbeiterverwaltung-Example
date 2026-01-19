package com.Mitarbeiterverwaltung.AuthorizationManagement.domain;

import java.util.Objects;

public final class EmployeeReference {

    private final int employeeNumber;

    public EmployeeReference(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public int getEmployeeNumber() {
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
        return employeeNumber == that.employeeNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeNumber);
    }

    @Override
    public String toString() {
        return Integer.toString(employeeNumber);
    }
}
