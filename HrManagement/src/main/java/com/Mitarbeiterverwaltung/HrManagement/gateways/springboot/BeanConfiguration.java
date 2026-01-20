package com.Mitarbeiterverwaltung.HrManagement.gateways.springboot;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Mitarbeiterverwaltung.HrManagement.gateways.db.EmployeeEntityRepository;
import com.Mitarbeiterverwaltung.HrManagement.gateways.db.EmployeeRepositoryImpl;
import com.Mitarbeiterverwaltung.HrManagement.gateways.db.EmploymentContractEntityRepository;
import com.Mitarbeiterverwaltung.HrManagement.gateways.messagequeue.EmploymentTerminatedEventPublisherImpl;
import com.Mitarbeiterverwaltung.HrManagement.gateways.messagequeue.EventPublisher;
import com.Mitarbeiterverwaltung.HrManagement.usecases.HrManagementServiceImpl;
import com.Mitarbeiterverwaltung.HrManagement.usecases.primary.HrManagementService;
import com.Mitarbeiterverwaltung.HrManagement.usecases.secondary.EmployeeRepository;
import com.Mitarbeiterverwaltung.HrManagement.usecases.secondary.EmploymentTerminatedEventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeanConfiguration {

	@Bean
	public EmployeeRepository employeeRepository(EmployeeEntityRepository employeeEntityRepository,
			EmploymentContractEntityRepository employmentContractEntityRepository) {
		return new EmployeeRepositoryImpl(employeeEntityRepository, employmentContractEntityRepository);
	}

	@Bean
	public EventPublisher eventPublisher(RabbitTemplate rabbitTemplate) {
		return new EventPublisher(rabbitTemplate);
	}

	@Bean
	public EmploymentTerminatedEventPublisher employmentTerminatedEventPublisher(EventPublisher eventPublisher,
			ObjectMapper objectMapper) {
		return new EmploymentTerminatedEventPublisherImpl(eventPublisher, objectMapper);
	}

	@Bean
	public HrManagementService hrManagementService(EmployeeRepository employeeRepository,
			EmploymentTerminatedEventPublisher employmentTerminatedEventPublisher) {
		return new HrManagementServiceImpl(employeeRepository, employmentTerminatedEventPublisher);
	}
}
