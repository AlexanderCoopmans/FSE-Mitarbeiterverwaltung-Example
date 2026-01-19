package com.Mitarbeiterverwaltung.DeviceManagement.gateways.messagequeue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary.DeviceManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EmploymentTerminatedEventListener {

	DeviceManagementService deviceManagementService;

	public EmploymentTerminatedEventListener(DeviceManagementService deviceManagementService) {
		this.deviceManagementService = deviceManagementService;
	}

	@RabbitListener(queues = "employment_terminated")
	public void receiveMessage(String message) {
		// Process the received message
		System.out.println("#################Received message:################# " + message);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			EmploymentTerminatedTO employmentTerminatedTO = objectMapper.readValue(message,
					EmploymentTerminatedTO.class);
			deviceManagementService.handleEmploymentTermination(employmentTerminatedTO.getEmployeeId(),
					employmentTerminatedTO.getTerminationDate());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
