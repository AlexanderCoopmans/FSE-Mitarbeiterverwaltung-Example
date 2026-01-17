package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.util.Objects;
import java.util.UUID;

public final class AssignmentId {
    private String id;

    public AssignmentId() {
        this.id = UUID.randomUUID().toString();
    }

    public AssignmentId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
