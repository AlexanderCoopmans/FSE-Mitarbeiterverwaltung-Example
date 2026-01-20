package com.Mitarbeiterverwaltung.DeviceManagement.domain.events;

import java.time.LocalDate;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.EmployeeReference;

public class AllDevicesReturnedEvent {
    EmployeeReference employeeReference;
    LocalDate lastReturnDate;

    public AllDevicesReturnedEvent(EmployeeReference employeeReference, LocalDate lastReturnDate) {
        this.employeeReference = employeeReference;
        this.lastReturnDate = lastReturnDate;
    }

    public EmployeeReference getEmployeeReference() {
        return employeeReference;
    }

    public LocalDate getLastReturnDate() {
        return lastReturnDate;
    }

}
