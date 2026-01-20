package com.Mitarbeiterverwaltung.HrManagement.usecases.secondary;

import java.time.LocalDate;

public interface EmploymentTerminatedEventPublisher {
    String publishEmploymentTermination(int employeeId, LocalDate terminationDate);
}
