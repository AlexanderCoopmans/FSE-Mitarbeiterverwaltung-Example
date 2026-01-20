package com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.messagequeue;

import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.primary.OffboardingStatus;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.secondary.OffboardingStatusEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PermissionEventPublisherImpl implements OffboardingStatusEventPublisher {

    private final EventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public PermissionEventPublisherImpl(EventPublisher eventPublisher, ObjectMapper objectMapper) {
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    @Override
    public String publishOffboardingStatus(int employeeId, OffboardingStatus status) {
        OffboardingStatusEventTO payloadBody = new OffboardingStatusEventTO(
                employeeId,
                status.allRevoked(),
                status.lastRevokedAt());

        String payload;
        try {
            payload = objectMapper.writeValueAsString(payloadBody);
        } catch (JsonProcessingException e) {
            System.out.println("Message could not be created. Cause: " + e.getMessage());
            return "Message could not be created.";
        }

        return eventPublisher.publishEvent("permissions.events", "offboarding.status", payload);
    }
}
