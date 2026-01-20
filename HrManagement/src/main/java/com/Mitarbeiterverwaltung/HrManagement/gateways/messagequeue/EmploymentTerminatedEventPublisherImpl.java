package com.Mitarbeiterverwaltung.HrManagement.gateways.messagequeue;

import java.time.LocalDate;

import com.Mitarbeiterverwaltung.HrManagement.usecases.secondary.EmploymentTerminatedEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmploymentTerminatedEventPublisherImpl implements EmploymentTerminatedEventPublisher {

    private final EventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public EmploymentTerminatedEventPublisherImpl(EventPublisher eventPublisher, ObjectMapper objectMapper) {
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    @Override
    public String publishEmploymentTermination(int employeeId, LocalDate terminationDate) {
        EmploymentTerminatedTO payloadBody = new EmploymentTerminatedTO(employeeId, terminationDate);
        String payload;
        try {
            payload = objectMapper.writeValueAsString(payloadBody);
        } catch (JsonProcessingException e) {
            System.out.println("Message could not be created. Cause: " + e.getMessage());
            return "Message could not be created.";
        }
        return eventPublisher.publishEvent("hr.events", "employment.terminated", payload);
    }
}
