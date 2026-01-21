package com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OffboardingStatusResponse {
    private LocalDate terminationDate;
    private String terminationReason;
    private String terminationStatus;
    private boolean systemPermissionsRevoked;
    private LocalDateTime lastSystemPermissionRevokedAt;
    private boolean devicesReturned;
    private LocalDateTime lastDevicesReturnedAt;

    public OffboardingStatusResponse() {
    }

    public OffboardingStatusResponse(LocalDate terminationDate, String terminationReason, String terminationStatus,
            boolean systemPermissionsRevoked, LocalDateTime lastSystemPermissionRevokedAt, boolean devicesReturned,
            LocalDateTime lastDevicesReturnedAt) {
        this.terminationDate = terminationDate;
        this.terminationReason = terminationReason;
        this.terminationStatus = terminationStatus;
        this.systemPermissionsRevoked = systemPermissionsRevoked;
        this.lastSystemPermissionRevokedAt = lastSystemPermissionRevokedAt;
        this.devicesReturned = devicesReturned;
        this.lastDevicesReturnedAt = lastDevicesReturnedAt;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getTerminationReason() {
        return terminationReason;
    }

    public void setTerminationReason(String terminationReason) {
        this.terminationReason = terminationReason;
    }

    public String getTerminationStatus() {
        return terminationStatus;
    }

    public void setTerminationStatus(String terminationStatus) {
        this.terminationStatus = terminationStatus;
    }

    public boolean isSystemPermissionsRevoked() {
        return systemPermissionsRevoked;
    }

    public void setSystemPermissionsRevoked(boolean systemPermissionsRevoked) {
        this.systemPermissionsRevoked = systemPermissionsRevoked;
    }

    public LocalDateTime getLastSystemPermissionRevokedAt() {
        return lastSystemPermissionRevokedAt;
    }

    public void setLastSystemPermissionRevokedAt(LocalDateTime lastSystemPermissionRevokedAt) {
        this.lastSystemPermissionRevokedAt = lastSystemPermissionRevokedAt;
    }

    public boolean isDevicesReturned() {
        return devicesReturned;
    }

    public void setDevicesReturned(boolean devicesReturned) {
        this.devicesReturned = devicesReturned;
    }

    public LocalDateTime getLastDevicesReturnedAt() {
        return lastDevicesReturnedAt;
    }

    public void setLastDevicesReturnedAt(LocalDateTime lastDevicesReturnedAt) {
        this.lastDevicesReturnedAt = lastDevicesReturnedAt;
    }
}
