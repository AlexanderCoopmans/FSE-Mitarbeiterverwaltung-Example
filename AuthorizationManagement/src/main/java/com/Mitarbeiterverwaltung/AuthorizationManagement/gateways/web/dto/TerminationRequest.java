package com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.web.dto;

import java.time.LocalDate;

public class TerminationRequest {
    private int employeeId;
    private LocalDate terminationDate;

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
