package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.time.LocalDate;
import java.util.Objects;

public class DeviceAssignment {
    private final AssignmentId assignmentId;
    private final EmployeeReference employee;
    private ValidityPeriod validityPeriod;

    public DeviceAssignment(AssignmentId assignmentId, EmployeeReference employee, ValidityPeriod validityPeriod) {
        this.assignmentId = assignmentId;
        this.employee = employee;
        this.validityPeriod = validityPeriod;
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

    public boolean isActiveOn(LocalDate date) {
        if (date == null) {
            return false;
        }
        return validityPeriod.contains(date);
    }

    public boolean blocks(ValidityPeriod period) {
        Objects.requireNonNull(period, "period must not be null");
        return validityPeriod.overlaps(period);
    }

    public void shortenValidityTo(LocalDate deadline) {
        Objects.requireNonNull(deadline, "deadline must not be null");
        validityPeriod = validityPeriod.shortenTo(deadline);
    }
}
