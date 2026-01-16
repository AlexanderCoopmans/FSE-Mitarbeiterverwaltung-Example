package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.util.Objects;

public final class DeviceNumber {
    private final String value;

    private DeviceNumber(String value) {
        this.value = value;
    }

    public static DeviceNumber of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Device number must not be null or blank");
        }
        return new DeviceNumber(value.trim());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceNumber)) {
            return false;
        }
        DeviceNumber that = (DeviceNumber) o;
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
