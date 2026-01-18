package com.Mitarbeiterverwaltung.DeviceManagement.gateways.messagequeue;

import java.time.LocalDate;

public class AllDevicesReturnedTO {
    String employeeId;
    LocalDate lastReturnDate;

    AllDevicesReturnedTO(String employeeId, LocalDate lastReturnDate) {
        this.employeeId = employeeId;
        this.lastReturnDate = lastReturnDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getLastReturnDate() {
        return lastReturnDate;
    }

    public void setLastReturnDate(LocalDate lastReturnDate) {
        this.lastReturnDate = lastReturnDate;
    }
}
