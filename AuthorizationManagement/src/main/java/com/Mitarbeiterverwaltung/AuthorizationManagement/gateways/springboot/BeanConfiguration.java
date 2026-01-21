package com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.springboot;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.db.PermissionEntityRepository;
import com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.db.PermissionRepositoryImpl;
import com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.messagequeue.EventPublisher;
import com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.messagequeue.PermissionEventPublisherImpl;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.PermissionManagementServiceImpl;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.primary.PermissionManagementService;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.secondary.OffboardingStatusEventPublisher;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.secondary.PermissionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeanConfiguration {

	private static final String PERMISSION_EVENTS_EXCHANGE = "permissions.events";
	private static final String OFFBOARDING_STATUS_QUEUE = "permissions_offboarding_status";
	private static final String ROUTING_OFFBOARDING_STATUS = "offboarding.status";

	@Bean
	public PermissionRepository permissionRepository(PermissionEntityRepository permissionEntityRepository) {
		return new PermissionRepositoryImpl(permissionEntityRepository);
	}

	@Bean
	public PermissionManagementService permissionManagementService(PermissionRepository permissionRepository) {
		return new PermissionManagementServiceImpl(permissionRepository);
	}

	@Bean
	public Queue employmentTerminatedQueue() {
		return new Queue("employment_terminated_permissions", true);
	}

	@Bean
	public TopicExchange permissionEventsExchange() {
		return new TopicExchange(PERMISSION_EVENTS_EXCHANGE, true, false);
	}

	@Bean
	public Queue offboardingStatusQueue() {
		return new Queue(OFFBOARDING_STATUS_QUEUE, true);
	}

	@Bean
	public Binding offboardingStatusBinding(Queue offboardingStatusQueue, TopicExchange permissionEventsExchange) {
		return BindingBuilder.bind(offboardingStatusQueue).to(permissionEventsExchange)
				.with(ROUTING_OFFBOARDING_STATUS);
	}

	@Bean
	public EventPublisher eventPublisher(RabbitTemplate rabbitTemplate) {
		return new EventPublisher(rabbitTemplate);
	}

	@Bean
	public OffboardingStatusEventPublisher offboardingStatusEventPublisher(EventPublisher eventPublisher,
			ObjectMapper objectMapper) {
		return new PermissionEventPublisherImpl(eventPublisher, objectMapper);
	}
}
