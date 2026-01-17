package com.Mitarbeiterverwaltung.HrManagement.domain;

import java.util.Objects;

public final class EmployeeNumber {
    private final int value;

    private EmployeeNumber(int value) {
        this.value = requireNonNull(value, "employeeNumber");
    }

    public static EmployeeNumber of(int value) {
        return new EmployeeNumber(value);
    }

    public int getValue() {
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
        return Integer.compare(value, that.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }


    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " must not be null");
        }
        return value;
    }
}
