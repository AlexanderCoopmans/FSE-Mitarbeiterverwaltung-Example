package com.Mitarbeiterverwaltung.HrManagement.usecases.secondary;

import java.time.LocalDate;

public interface EmploymentTerminatedEventPublisher {

    String publishTermination(int employeeId, LocalDate terminationDate);
}
