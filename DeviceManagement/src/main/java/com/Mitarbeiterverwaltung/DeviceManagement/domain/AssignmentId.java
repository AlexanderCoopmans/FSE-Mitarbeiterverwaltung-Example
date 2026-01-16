package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.util.Objects;
import java.util.UUID;

public final class AssignmentId {
    private final String value;

    private AssignmentId(String value) {
        this.value = value;
    }

    public static AssignmentId newId() {
        return new AssignmentId(UUID.randomUUID().toString());
    }

    public static AssignmentId of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Assignment id must not be null or blank");
        }
        return new AssignmentId(value.trim());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssignmentId)) {
            return false;
        }
        AssignmentId that = (AssignmentId) o;
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
}
