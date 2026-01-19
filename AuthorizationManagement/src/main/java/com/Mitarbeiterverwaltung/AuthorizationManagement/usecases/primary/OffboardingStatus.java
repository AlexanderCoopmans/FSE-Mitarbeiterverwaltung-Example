package com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.primary;

import java.time.LocalDate;

public record OffboardingStatus(boolean allRevoked, LocalDate lastRevokedAt) {
}
