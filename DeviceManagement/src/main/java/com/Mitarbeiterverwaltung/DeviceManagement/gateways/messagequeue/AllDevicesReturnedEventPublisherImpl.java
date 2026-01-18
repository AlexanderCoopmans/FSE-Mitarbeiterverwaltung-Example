package com.Mitarbeiterverwaltung.DeviceManagement.gateways.messagequeue;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.events.AllDevicesReturnedEvent;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary.AllDevicesReturnedEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AllDevicesReturnedEventPublisherImpl implements AllDevicesReturnedEventPublisher {

    private final EventPublisher eventPublisher;

    public AllDevicesReturnedEventPublisherImpl(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public String publishDomainEvent(AllDevicesReturnedEvent event) {
        AllDevicesReturnedTO payloadBody = new AllDevicesReturnedTO(
                event.getEmployeeReference().getEmployeeNumber(),
                event.getlastReturnDate());

        String payload = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            payload = objectMapper.writeValueAsString(payloadBody);
        } catch (JsonProcessingException e) {
            System.out.println("Message could not be created. Cause: " + e.getMessage());
            return "Message could not be created.";
        }

        eventPublisher.publishEvent("devices.events", "devices.returned", payload);
        return payload;
    }
}
