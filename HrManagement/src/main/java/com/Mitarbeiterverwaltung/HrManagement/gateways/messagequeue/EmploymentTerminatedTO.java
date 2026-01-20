package com.Mitarbeiterverwaltung.HrManagement.gateways.messagequeue;

import java.time.LocalDate;

public class EmploymentTerminatedTO {
    private int employeeId;
    private LocalDate terminationDate;

    public EmploymentTerminatedTO() {
    }

    public EmploymentTerminatedTO(int employeeId, LocalDate terminationDate) {
        this.employeeId = employeeId;
        this.terminationDate = terminationDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }
}
