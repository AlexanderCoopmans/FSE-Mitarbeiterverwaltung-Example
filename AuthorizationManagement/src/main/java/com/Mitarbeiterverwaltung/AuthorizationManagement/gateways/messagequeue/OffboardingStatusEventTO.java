package com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.messagequeue;

import java.time.LocalDate;

public class OffboardingStatusEventTO {
    int employeeId;
    boolean allRevoked;
    LocalDate lastRevokedAt;

    public OffboardingStatusEventTO() {
    }

    public OffboardingStatusEventTO(int employeeId, boolean allRevoked, LocalDate lastRevokedAt) {
        this.employeeId = employeeId;
        this.allRevoked = allRevoked;
        this.lastRevokedAt = lastRevokedAt;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isAllRevoked() {
        return allRevoked;
    }

    public void setAllRevoked(boolean allRevoked) {
        this.allRevoked = allRevoked;
    }

    public LocalDate getLastRevokedAt() {
        return lastRevokedAt;
    }

    public void setLastRevokedAt(LocalDate lastRevokedAt) {
        this.lastRevokedAt = lastRevokedAt;
    }
}
