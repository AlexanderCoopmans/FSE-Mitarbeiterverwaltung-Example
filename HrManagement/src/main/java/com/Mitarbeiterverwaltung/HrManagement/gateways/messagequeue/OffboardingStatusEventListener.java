package com.Mitarbeiterverwaltung.HrManagement.gateways.messagequeue;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.Mitarbeiterverwaltung.HrManagement.usecases.primary.HrManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OffboardingStatusEventListener {

    private final HrManagementService hrManagementService;
    private final ObjectMapper objectMapper;

    public OffboardingStatusEventListener(HrManagementService hrManagementService, ObjectMapper objectMapper) {
        this.hrManagementService = hrManagementService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "permissions_offboarding_status")
    public void receiveMessage(String message) {
        try {
            OffboardingStatusEventTO payload = objectMapper.readValue(message, OffboardingStatusEventTO.class);
            if (payload.isAllRevoked() && payload.getLastRevokedAt() != null) {
                LocalDateTime revokedAt = payload.getLastRevokedAt().atTime(LocalTime.MIDNIGHT);
                hrManagementService.recordSystemPermissionsRevoked(payload.getEmployeeId(), revokedAt);
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
