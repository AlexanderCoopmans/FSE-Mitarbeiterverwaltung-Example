package com.Mitarbeiterverwaltung.AuthorizationManagement.domain;

import java.util.Objects;

public final class ApplicationSystem {

    private final String systemName;

    public ApplicationSystem(String systemName) {
        this.systemName = requireText(systemName, "systemName");
    }

    public String getSystemName() {
        return systemName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApplicationSystem that = (ApplicationSystem) o;
        return systemName.equals(that.systemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemName);
    }

    @Override
    public String toString() {
        return systemName;
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
