package com.Mitarbeiterverwaltung.HrManagement.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public final class TerminationProcessInformation {
    private final LocalDate terminationDate;
    private final String terminationReason;
    private final TerminationStatus status;
    private final boolean statusSystemPermissionsRevoked;
    private final LocalDateTime lastSystemPermissionRevokedAt;
    private final boolean statusDevicesReturned;
    private final LocalDateTime lastDevicesReturnedAt;

    private TerminationProcessInformation(
            LocalDate terminationDate,
            String terminationReason,
            TerminationStatus status,
            boolean statusSystemPermissionsRevoked,
            LocalDateTime lastSystemPermissionRevokedAt,
            boolean statusDevicesReturned,
            LocalDateTime lastDevicesReturnedAt) {
        this.terminationDate = requireNonNull(terminationDate, "terminationDate");
        this.terminationReason = requireNonBlank(terminationReason, "terminationReason");
        this.status = requireNonNull(status, "status");
        this.statusSystemPermissionsRevoked = statusSystemPermissionsRevoked;
        this.lastSystemPermissionRevokedAt = lastSystemPermissionRevokedAt;
        this.statusDevicesReturned = statusDevicesReturned;
        this.lastDevicesReturnedAt = lastDevicesReturnedAt;
    }

    public static TerminationProcessInformation start(LocalDate terminationDate, String terminationReason) {
        return new TerminationProcessInformation(
                terminationDate,
                terminationReason,
                TerminationStatus.IN_PROGRESS,
                false,
                null,
                false,
                null);
    }

    public static TerminationProcessInformation restore(LocalDate terminationDate,
            String terminationReason,
            TerminationStatus status,
            boolean statusSystemPermissionsRevoked,
            LocalDateTime lastSystemPermissionRevokedAt,
            boolean statusDevicesReturned,
            LocalDateTime lastDevicesReturnedAt) {
        return new TerminationProcessInformation(terminationDate, terminationReason, status,
                statusSystemPermissionsRevoked, lastSystemPermissionRevokedAt,
                statusDevicesReturned, lastDevicesReturnedAt);
    }

    public TerminationProcessInformation markSystemPermissionsRevoked(LocalDateTime revokedAt) {
        LocalDateTime timestamp = requireNonNull(revokedAt, "revokedAt");
        boolean permissionsRevoked = true;
        TerminationStatus newStatus = determineStatus(permissionsRevoked, statusDevicesReturned);
        return new TerminationProcessInformation(
                terminationDate,
                terminationReason,
                newStatus,
                permissionsRevoked,
                timestamp,
                statusDevicesReturned,
                lastDevicesReturnedAt);
    }

    public TerminationProcessInformation markDevicesReturned(LocalDateTime returnedAt) {
        LocalDateTime timestamp = requireNonNull(returnedAt, "returnedAt");
        boolean devicesReturned = true;
        TerminationStatus newStatus = determineStatus(statusSystemPermissionsRevoked, devicesReturned);
        return new TerminationProcessInformation(
                terminationDate,
                terminationReason,
                newStatus,
                statusSystemPermissionsRevoked,
                lastSystemPermissionRevokedAt,
                devicesReturned,
                timestamp);
    }

    private TerminationStatus determineStatus(boolean permissionsRevoked, boolean devicesReturned) {
        if (permissionsRevoked && devicesReturned) {
            return TerminationStatus.COMPLETED;
        }
        return TerminationStatus.IN_PROGRESS;
    }

    public boolean isCompleted() {
        return status == TerminationStatus.COMPLETED;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public String getTerminationReason() {
        return terminationReason;
    }

    public TerminationStatus getStatus() {
        return status;
    }

    public boolean isStatusSystemPermissionsRevoked() {
        return statusSystemPermissionsRevoked;
    }

    public LocalDateTime getLastSystemPermissionRevokedAt() {
        return lastSystemPermissionRevokedAt;
    }

    public boolean isStatusDevicesReturned() {
        return statusDevicesReturned;
    }

    public LocalDateTime getLastDevicesReturnedAt() {
        return lastDevicesReturnedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TerminationProcessInformation)) {
            return false;
        }
        TerminationProcessInformation that = (TerminationProcessInformation) o;
        return statusSystemPermissionsRevoked == that.statusSystemPermissionsRevoked
                && statusDevicesReturned == that.statusDevicesReturned
                && terminationDate.equals(that.terminationDate)
                && terminationReason.equals(that.terminationReason)
                && status == that.status
                && Objects.equals(lastSystemPermissionRevokedAt, that.lastSystemPermissionRevokedAt)
                && Objects.equals(lastDevicesReturnedAt, that.lastDevicesReturnedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                terminationDate,
                terminationReason,
                status,
                statusSystemPermissionsRevoked,
                lastSystemPermissionRevokedAt,
                statusDevicesReturned,
                lastDevicesReturnedAt);
    }

    @Override
    public String toString() {
        return "Termination on " + terminationDate + " because '" + terminationReason + "' status=" + status;
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " must not be null");
        }
        return value;
    }
}
