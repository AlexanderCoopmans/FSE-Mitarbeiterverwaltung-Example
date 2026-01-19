package com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.web.dto;

import java.time.LocalDate;

public class PermissionUpdateRequest {
    private String role;
    private LocalDate validTo;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
}
