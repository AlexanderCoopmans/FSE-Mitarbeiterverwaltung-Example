package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

public class Device {
    private final DeviceId deviceId;
    private final DeviceType deviceType;
    private final String manufacturer;
    private final String designation;
    private DeviceAssignment currentAssignment = null;

    public Device(DeviceId deviceId, DeviceType deviceType, String manufacturer, String designation) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.manufacturer = manufacturer;
        this.designation = designation;
    }

    public Device(DeviceId deviceId, DeviceType deviceType, String manufacturer, String designation,
            DeviceAssignment currentAssignment) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.manufacturer = manufacturer;
        this.designation = designation;
        this.currentAssignment = currentAssignment;
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

    public DeviceAssignment assignToEmployee(EmployeeReference employee, ValidityPeriod period) {
        Objects.requireNonNull(employee, "employee must not be null");
        Objects.requireNonNull(period, "period must not be null");
        ensureNoActiveAssignment();
        DeviceAssignment assignment = new DeviceAssignment(new AssignmentId(), employee, period);
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

    private void ensureNoActiveAssignment() {
        if (currentAssignment != null && !currentAssignment.isReturned()) {
            throw new IllegalStateException("Device already has an active assignment");
        }
    }
}
