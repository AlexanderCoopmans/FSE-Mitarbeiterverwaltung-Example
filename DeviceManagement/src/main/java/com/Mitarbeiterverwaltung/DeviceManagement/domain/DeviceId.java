package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.util.Objects;

public final class DeviceId {
    private int id;

    public DeviceId(int id) {
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
