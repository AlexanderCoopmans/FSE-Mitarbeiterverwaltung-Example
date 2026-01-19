package com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.springboot;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.db.PermissionEntityRepository;
import com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.db.PermissionRepositoryImpl;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.PermissionManagementServiceImpl;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.primary.PermissionManagementService;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.secondary.PermissionRepository;

@Configuration
public class BeanConfiguration {

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
		return new Queue("employment_terminated", true);
	}
}
