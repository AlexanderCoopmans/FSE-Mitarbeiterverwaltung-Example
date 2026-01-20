package com.Mitarbeiterverwaltung.HrManagement.gateways.messagequeue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.Mitarbeiterverwaltung.HrManagement.usecases.primary.HrManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AllDevicesReturnedEventListener {

    private final HrManagementService hrManagementService;
    private final ObjectMapper objectMapper;

    public AllDevicesReturnedEventListener(HrManagementService hrManagementService, ObjectMapper objectMapper) {
        this.hrManagementService = hrManagementService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "devices_all_returned")
    public void receiveMessage(String message) {
        System.out.println("#################Received message:################# " + message);
        try {
            AllDevicesReturnedTO returnedTO = objectMapper.readValue(message, AllDevicesReturnedTO.class);
            hrManagementService.handleAllDevicesReturned(returnedTO.getEmployeeId(), returnedTO.getLastReturnDate());
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
