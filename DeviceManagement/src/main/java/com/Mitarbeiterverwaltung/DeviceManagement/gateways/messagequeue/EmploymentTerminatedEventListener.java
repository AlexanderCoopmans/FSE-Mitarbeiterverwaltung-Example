package com.Mitarbeiterverwaltung.DeviceManagement.gateways.messagequeue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary.DeviceManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EmploymentTerminatedEventListener {

	private final DeviceManagementService deviceManagementService;
	private final ObjectMapper objectMapper;

	public EmploymentTerminatedEventListener(DeviceManagementService deviceManagementService,
			ObjectMapper objectMapper) {
		this.deviceManagementService = deviceManagementService;
		this.objectMapper = objectMapper;
	}

	@RabbitListener(queues = "employment_terminated_devices")
	public void receiveMessage(String message) {
		System.out.println("#################Received message:################# " + message);
		try {
			EmploymentTerminatedTO employmentTerminatedTO = objectMapper.readValue(message,
					EmploymentTerminatedTO.class);
			deviceManagementService.handleEmploymentTermination(
					employmentTerminatedTO.getEmployeeId(), employmentTerminatedTO.getTerminationDate());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
