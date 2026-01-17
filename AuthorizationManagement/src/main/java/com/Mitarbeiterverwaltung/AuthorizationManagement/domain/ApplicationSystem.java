package com.Mitarbeiterverwaltung.AuthorizationManagement.domain;

import java.util.Objects;

public final class ApplicationSystem {

    private final String systemName;
    private final String systemDescription;

    public ApplicationSystem(String systemName, String systemDescription) {
        this.systemName = requireText(systemName, "systemName");
        this.systemDescription = requireText(systemDescription, "systemDescription");
    }

    public String getSystemName() {
        return systemName;
    }

    public String getSystemDescription() {
        return systemDescription;
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
        return systemName.equals(that.systemName) && systemDescription.equals(that.systemDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemName, systemDescription);
    }

    @Override
    public String toString() {
        return systemName + " (" + systemDescription + ")";
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
