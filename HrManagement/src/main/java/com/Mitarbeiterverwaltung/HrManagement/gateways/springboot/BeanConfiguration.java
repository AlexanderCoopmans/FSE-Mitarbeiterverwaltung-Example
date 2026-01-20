package com.Mitarbeiterverwaltung.HrManagement.gateways.springboot;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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

	private static final String HR_EVENTS_EXCHANGE = "hr.events";
	private static final String HR_ROUTING_EMPLOYMENT_TERMINATED = "employment.terminated";
	private static final String DEVICES_EVENTS_EXCHANGE = "devices.events";
	private static final String DEVICES_ROUTING_ALL_RETURNED = "devices.returned";
	private static final String DEVICES_ALL_RETURNED_QUEUE = "devices_all_returned";

	@Bean
	public EmployeeRepository employeeRepository(EmployeeEntityRepository employeeEntityRepository,
			EmploymentContractEntityRepository employmentContractEntityRepository) {
		return new EmployeeRepositoryImpl(employeeEntityRepository, employmentContractEntityRepository);
	}

	@Bean
	public DirectExchange hrEventsExchange() {
		return new DirectExchange(HR_EVENTS_EXCHANGE, true, false);
	}

	@Bean
	public DirectExchange devicesEventsExchange() {
		return new DirectExchange(DEVICES_EVENTS_EXCHANGE, true, false);
	}

	@Bean
	public Queue devicesAllReturnedQueue() {
		return new Queue(DEVICES_ALL_RETURNED_QUEUE, true);
	}

	@Bean
	public Binding devicesAllReturnedBinding(Queue devicesAllReturnedQueue, DirectExchange devicesEventsExchange) {
		return BindingBuilder.bind(devicesAllReturnedQueue)
				.to(devicesEventsExchange)
				.with(DEVICES_ROUTING_ALL_RETURNED);
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
