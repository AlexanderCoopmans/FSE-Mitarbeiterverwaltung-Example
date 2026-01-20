package com.Mitarbeiterverwaltung.DeviceManagement.gateways.messagequeue;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.events.AllDevicesReturnedEvent;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary.AllDevicesReturnedEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AllDevicesReturnedEventPublisherImpl implements AllDevicesReturnedEventPublisher {

    private final EventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public AllDevicesReturnedEventPublisherImpl(EventPublisher eventPublisher, ObjectMapper objectMapper) {
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    @Override
    public String publishDomainEvent(AllDevicesReturnedEvent event) {
        AllDevicesReturnedTO payloadBody = new AllDevicesReturnedTO(
                event.getEmployeeReference().getEmployeeNumber(),
                event.getLastReturnDate());

        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(payloadBody);
        } catch (JsonProcessingException e) {
            System.out.println("Message could not be created. Cause: " + e.getMessage());
            return "Message could not be created.";
        }

        eventPublisher.publishEvent("devices.events", "devices.returned", payload);
        return payload;
    }
}
