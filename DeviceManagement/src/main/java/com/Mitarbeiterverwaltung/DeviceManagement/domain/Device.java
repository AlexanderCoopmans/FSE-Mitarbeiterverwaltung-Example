package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Device {
    private final DeviceNumber deviceNumber;
    private final DeviceType deviceType;
    private final String manufacturer;
    private final String designation;
    private final List<DeviceAssignment> assignments = new ArrayList<>();

    private Device(DeviceNumber deviceNumber, DeviceType deviceType, String manufacturer, String designation) {
        this.deviceNumber = deviceNumber;
        this.deviceType = deviceType;
        this.manufacturer = manufacturer;
        this.designation = designation;
    }

    public static Device of(DeviceNumber number, DeviceType type, String manufacturer, String designation) {
        Objects.requireNonNull(number, "device number must not be null");
        Objects.requireNonNull(type, "device type must not be null");
        if (manufacturer == null || manufacturer.isBlank()) {
            throw new IllegalArgumentException("Manufacturer must not be null or blank");
        }
        if (designation == null || designation.isBlank()) {
            throw new IllegalArgumentException("Designation must not be null or blank");
        }
        return new Device(number, type, manufacturer.trim(), designation.trim());
    }

    public DeviceNumber getDeviceNumber() {
        return deviceNumber;
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
        ensureNoOverlappingActiveAssignment(period);
        DeviceAssignment assignment = DeviceAssignment.of(employee, period);
        assignments.add(assignment);
        return assignment;
    }

    public void recordReturn(AssignmentId assignmentId, LocalDate returnDate) {
        Objects.requireNonNull(assignmentId, "assignment id must not be null");
        Objects.requireNonNull(returnDate, "return date must not be null");
        DeviceAssignment assignment = findAssignment(assignmentId);
        assignment.markReturned(returnDate);
    }

    public void enforceReturnBy(EmployeeReference employee, LocalDate deadline) {
        Objects.requireNonNull(employee, "employee must not be null");
        Objects.requireNonNull(deadline, "deadline must not be null");
        assignments.stream()
                .filter(a -> a.getEmployee().equals(employee))
                .filter(a -> !a.isReturned())
                .forEach(a -> a.shortenValidityTo(deadline));
    }

    public List<DeviceAssignment> assignmentsForEmployee(EmployeeReference employee) {
        Objects.requireNonNull(employee, "employee must not be null");
        List<DeviceAssignment> result = new ArrayList<>();
        for (DeviceAssignment assignment : assignments) {
            if (assignment.getEmployee().equals(employee)) {
                result.add(assignment);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<DeviceAssignment> assignmentsDueInMonth(YearMonth month) {
        Objects.requireNonNull(month, "month must not be null");
        List<DeviceAssignment> result = new ArrayList<>();
        for (DeviceAssignment assignment : assignments) {
            if (!assignment.isReturned() && assignment.getValidityPeriod().endsInMonth(month)) {
                result.add(assignment);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public boolean isAssignedTo(EmployeeReference employee) {
        return assignments.stream().anyMatch(a -> a.getEmployee().equals(employee) && !a.isReturned());
    }

    private void ensureNoOverlappingActiveAssignment(ValidityPeriod period) {
        for (DeviceAssignment assignment : assignments) {
            if (assignment.blocks(period)) {
                throw new IllegalStateException("Device already assigned for the requested period");
            }
        }
    }

    private DeviceAssignment findAssignment(AssignmentId assignmentId) {
        return assignments.stream()
                .filter(a -> a.getAssignmentId().equals(assignmentId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found: " + assignmentId.getValue()));
    }
}
