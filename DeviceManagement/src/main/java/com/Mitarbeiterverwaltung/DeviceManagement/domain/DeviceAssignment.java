package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.time.LocalDate;
import java.util.Objects;

public class DeviceAssignment {
    private final AssignmentId assignmentId;
    private final EmployeeReference employee;
    private ValidityPeriod validityPeriod;
    private LocalDate returnedOn;

    public DeviceAssignment(AssignmentId assignmentId, EmployeeReference employee, ValidityPeriod validityPeriod) {
        this.assignmentId = assignmentId;
        this.employee = employee;
        this.validityPeriod = validityPeriod;
    }

    public DeviceAssignment(AssignmentId assignmentId, EmployeeReference employee, ValidityPeriod validityPeriod,
            LocalDate returnedOn) {
        this.assignmentId = assignmentId;
        this.employee = employee;
        this.validityPeriod = validityPeriod;
        this.returnedOn = returnedOn;
    }

    public AssignmentId getAssignmentId() {
        return assignmentId;
    }

    public EmployeeReference getEmployee() {
        return employee;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public LocalDate getReturnedOn() {
        return returnedOn;
    }

    public boolean isReturned() {
        return returnedOn != null;
    }

    public boolean isActiveOn(LocalDate date) {
        if (date == null) {
            return false;
        }
        if (!validityPeriod.contains(date)) {
            return false;
        }
        return returnedOn == null || !date.isAfter(returnedOn);
    }

    public boolean blocks(ValidityPeriod period) {
        Objects.requireNonNull(period, "period must not be null");
        if (!validityPeriod.overlaps(period)) {
            return false;
        }
        if (returnedOn == null) {
            return true;
        }
        return !returnedOn.isBefore(period.getStartDate());
    }

    public void markReturned(LocalDate returnDate) {
        Objects.requireNonNull(returnDate, "return date must not be null");
        if (isReturned()) {
            return;
        }
        if (returnDate.isBefore(validityPeriod.getStartDate())) {
            throw new IllegalArgumentException("Return date cannot be before assignment start date");
        }
        returnedOn = returnDate;
    }

    public void shortenValidityTo(LocalDate deadline) {
        Objects.requireNonNull(deadline, "deadline must not be null");
        validityPeriod = validityPeriod.shortenTo(deadline);
    }
}
