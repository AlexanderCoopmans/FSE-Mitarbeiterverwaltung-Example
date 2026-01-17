package com.Mitarbeiterverwaltung.AuthorizationManagement.domain;

import java.util.Objects;
import java.util.UUID;

public final class PermissionId {

    private final UUID value;

    private PermissionId(UUID value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    public static PermissionId of(UUID value) {
        return new PermissionId(value);
    }

    public static PermissionId newId() {
        return new PermissionId(UUID.randomUUID());
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PermissionId that = (PermissionId) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
