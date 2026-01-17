package com.Mitarbeiterverwaltung.HrManagement.domain;

import java.util.Objects;

public final class EmployeeNumber {
    private final String value;

    private EmployeeNumber(String value) {
        this.value = requireNonBlank(value, "employeeNumber");
    }

    public static EmployeeNumber of(String value) {
        return new EmployeeNumber(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeNumber)) {
            return false;
        }
        EmployeeNumber that = (EmployeeNumber) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
