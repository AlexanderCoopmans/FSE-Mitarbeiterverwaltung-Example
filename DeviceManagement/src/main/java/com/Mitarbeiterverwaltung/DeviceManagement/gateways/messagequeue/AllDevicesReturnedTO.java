package com.Mitarbeiterverwaltung.DeviceManagement.gateways.messagequeue;

import java.time.LocalDate;

public class AllDevicesReturnedTO {
    int employeeId;
    LocalDate lastReturnDate;

    AllDevicesReturnedTO(int employeeId, LocalDate lastReturnDate) {
        this.employeeId = employeeId;
        this.lastReturnDate = lastReturnDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getLastReturnDate() {
        return lastReturnDate;
    }

    public void setLastReturnDate(LocalDate lastReturnDate) {
        this.lastReturnDate = lastReturnDate;
    }
}
