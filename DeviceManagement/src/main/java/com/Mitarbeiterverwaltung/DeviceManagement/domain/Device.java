package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Device {
    private final DeviceId deviceId;
    private final DeviceType deviceType;
    private final String manufacturer;
    private final String designation;
    private final List<DeviceAssignment> assignments = new ArrayList<>();
    private DeviceAssignment currentAssignment;

    private Device(DeviceId deviceId, DeviceType deviceType, String manufacturer, String designation) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.manufacturer = manufacturer;
        this.designation = designation;
    }

    public static Device of(DeviceId id, DeviceType type, String manufacturer, String designation) {
        Objects.requireNonNull(id, "device id must not be null");
        Objects.requireNonNull(type, "device type must not be null");
        if (manufacturer == null || manufacturer.isBlank()) {
            throw new IllegalArgumentException("Manufacturer must not be null or blank");
        }
        if (designation == null || designation.isBlank()) {
            throw new IllegalArgumentException("Designation must not be null or blank");
        }
        return new Device(id, type, manufacturer.trim(), designation.trim());
    }

    public DeviceId getDeviceId() {
        return deviceId;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getDesignation() {
        return designation;
    }

    public List<DeviceAssignment> getAssignments() {
        return Collections.unmodifiableList(assignments);
    }

    public DeviceAssignment assignToEmployee(EmployeeReference employee, ValidityPeriod period) {
        Objects.requireNonNull(employee, "employee must not be null");
        Objects.requireNonNull(period, "period must not be null");
        ensureNoActiveAssignment();
        ensureNoOverlappingActiveAssignment(period);
        DeviceAssignment assignment = DeviceAssignment.of(employee, period);
        assignments.add(assignment);
        currentAssignment = assignment;
        return assignment;
    }

    public void recordReturn(LocalDate returnDate) {
        Objects.requireNonNull(returnDate, "return date must not be null");
        currentAssignment.markReturned(returnDate);
        currentAssignment = null;
    }

    public void enforceReturnBy(LocalDate deadline) {
        Objects.requireNonNull(deadline, "deadline must not be null");
        if (currentAssignment != null
                && !currentAssignment.isReturned()) {
            currentAssignment.shortenValidityTo(deadline);
        }
    }

    public boolean isReturnDueInMonth(YearMonth month) {
        Objects.requireNonNull(month, "month must not be null");
        return currentAssignment != null
                && !currentAssignment.isReturned()
                && currentAssignment.getValidityPeriod().endsInMonth(month);
    }

    public boolean isAssignedTo(EmployeeReference employee) {
        Objects.requireNonNull(employee, "employee must not be null");
        return currentAssignment != null
                && !currentAssignment.isReturned()
                && currentAssignment.getEmployee().equals(employee);
    }

    private void ensureNoOverlappingActiveAssignment(ValidityPeriod period) {
        for (DeviceAssignment assignment : assignments) {
            if (assignment.blocks(period)) {
                throw new IllegalStateException("Device already assigned for the requested period");
            }
        }
    }

    private void ensureNoActiveAssignment() {
        if (currentAssignment != null && !currentAssignment.isReturned()) {
            throw new IllegalStateException("Device already has an active assignment");
        }
    }
}
