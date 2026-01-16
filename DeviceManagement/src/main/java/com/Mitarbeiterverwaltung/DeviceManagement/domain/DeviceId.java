package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.util.Objects;
import java.util.UUID;

public final class DeviceId {
    private final String value;

    private DeviceId(String value) {
        this.value = value;
    }

    public static DeviceId newId() {
        return new DeviceId(UUID.randomUUID().toString());
    }

    public static DeviceId of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Device id must not be null or blank");
        }
        return new DeviceId(value.trim());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceId)) {
            return false;
        }
        DeviceId that = (DeviceId) o;
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
