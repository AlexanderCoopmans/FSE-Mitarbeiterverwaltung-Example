package com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.web.dto;

import java.time.LocalDate;

public class OffboardingStatusResponse {
    private boolean allRevoked;
    private LocalDate lastRevokedAt;

    public OffboardingStatusResponse() {
    }

    public OffboardingStatusResponse(boolean allRevoked, LocalDate lastRevokedAt) {
        this.allRevoked = allRevoked;
        this.lastRevokedAt = lastRevokedAt;
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
