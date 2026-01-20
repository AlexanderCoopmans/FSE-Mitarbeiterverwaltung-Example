package com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.secondary;

import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.primary.OffboardingStatus;

public interface OffboardingStatusEventPublisher {

    String publishOffboardingStatus(int employeeId, OffboardingStatus status);
}
