package com.Mitarbeiterverwaltung.DeviceManagement.gateways.springboot;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Mitarbeiterverwaltung.DeviceManagement.gateways.db.DeviceEntityRepository;
import com.Mitarbeiterverwaltung.DeviceManagement.gateways.db.DeviceRepositoryImpl;
import com.Mitarbeiterverwaltung.DeviceManagement.gateways.messagequeue.AllDevicesReturnedEventPublisherImpl;
import com.Mitarbeiterverwaltung.DeviceManagement.gateways.messagequeue.EventPublisher;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.DeviceManagementServiceImpl;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary.DeviceManagementService;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary.AllDevicesReturnedEventPublisher;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeanConfiguration {

    @Bean
    public DeviceRepository deviceRepository(DeviceEntityRepository deviceEntityRepository) {
        return new DeviceRepositoryImpl(deviceEntityRepository);
    }

    @Bean
    public EventPublisher eventPublisher(RabbitTemplate rabbitTemplate) {
        return new EventPublisher(rabbitTemplate);
    }

    @Bean
    public AllDevicesReturnedEventPublisher allDevicesReturnedEventPublisher(EventPublisher eventPublisher,
            ObjectMapper objectMapper) {
        return new AllDevicesReturnedEventPublisherImpl(eventPublisher, objectMapper);
    }

    @Bean
    public DeviceManagementService deviceManagementService(DeviceRepository deviceRepository,
            AllDevicesReturnedEventPublisher allDevicesReturnedEventPublisher) {
        return new DeviceManagementServiceImpl(deviceRepository, allDevicesReturnedEventPublisher);
    }

    @Bean
    public Queue employmentTerminatedQueue() {
        return new Queue("employment_terminated", true);
    }
}
