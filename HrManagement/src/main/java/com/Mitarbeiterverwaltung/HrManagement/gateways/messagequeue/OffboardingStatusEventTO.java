package com.Mitarbeiterverwaltung.HrManagement.gateways.messagequeue;

import java.time.LocalDate;

public class OffboardingStatusEventTO {
    private int employeeId;
    private boolean allRevoked;
    private LocalDate lastRevokedAt;

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
