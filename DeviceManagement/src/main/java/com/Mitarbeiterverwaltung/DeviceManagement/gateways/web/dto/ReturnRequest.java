package com.Mitarbeiterverwaltung.DeviceManagement.gateways.web.dto;

import java.time.LocalDate;

public class ReturnRequest {
    private LocalDate returnedAt;

    public LocalDate getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDate returnedAt) {
        this.returnedAt = returnedAt;
    }
}
