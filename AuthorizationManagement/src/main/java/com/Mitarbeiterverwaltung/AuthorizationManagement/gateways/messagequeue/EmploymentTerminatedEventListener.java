package com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.messagequeue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.primary.PermissionManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EmploymentTerminatedEventListener {

    private final PermissionManagementService permissionManagementService;
    private final ObjectMapper objectMapper;

    public EmploymentTerminatedEventListener(PermissionManagementService permissionManagementService,
            ObjectMapper objectMapper) {
        this.permissionManagementService = permissionManagementService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "employment_terminated_permissions")
    public void receiveMessage(String message) {
        System.out.println("#################Received message:################# " + message);
        try {
            EmploymentTerminatedTO employmentTerminatedTO = objectMapper.readValue(message,
                    EmploymentTerminatedTO.class);
            permissionManagementService.terminateEmployeePermissions(employmentTerminatedTO.getEmployeeId(),
                    employmentTerminatedTO.getTerminationDate());
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
