package com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OffboardingStatusResponse {
    private LocalDate terminationDate;
    private String reason;
    private String status;
    private boolean systemPermissionsRevoked;
    private LocalDateTime lastSystemPermissionRevokedAt;
    private boolean devicesReturned;
    private LocalDateTime lastDevicesReturnedAt;

    public OffboardingStatusResponse(LocalDate terminationDate, String reason, String status,
            boolean systemPermissionsRevoked, LocalDateTime lastSystemPermissionRevokedAt, boolean devicesReturned,
            LocalDateTime lastDevicesReturnedAt) {
        this.terminationDate = terminationDate;
        this.reason = reason;
        this.status = status;
        this.systemPermissionsRevoked = systemPermissionsRevoked;
        this.lastSystemPermissionRevokedAt = lastSystemPermissionRevokedAt;
        this.devicesReturned = devicesReturned;
        this.lastDevicesReturnedAt = lastDevicesReturnedAt;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSystemPermissionsRevoked() {
        return systemPermissionsRevoked;
    }

    public LocalDateTime getLastSystemPermissionRevokedAt() {
        return lastSystemPermissionRevokedAt;
    }

    public boolean isDevicesReturned() {
        return devicesReturned;
    }

    public LocalDateTime getLastDevicesReturnedAt() {
        return lastDevicesReturnedAt;
    }
}
