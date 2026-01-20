package com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto;

import java.time.LocalDate;

public class TerminateContractRequest {
    private LocalDate terminationDate;
    private String reason;

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
