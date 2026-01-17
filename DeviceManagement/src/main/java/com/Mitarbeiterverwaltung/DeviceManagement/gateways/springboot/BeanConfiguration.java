package com.Mitarbeiterverwaltung.DeviceManagement.gateways.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Mitarbeiterverwaltung.DeviceManagement.gateways.db.DeviceEntityRepository;
import com.Mitarbeiterverwaltung.DeviceManagement.gateways.db.DeviceRepositoryImpl;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.DeviceManagmentServiceImpl;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary.DeviceManagmentService;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary.DeviceRepository;

@Configuration
public class BeanConfiguration {

    @Bean
    public DeviceRepository deviceRepository(DeviceEntityRepository deviceEntityRepository) {
        return new DeviceRepositoryImpl(deviceEntityRepository);
    }

    @Bean
    public DeviceManagmentService deviceManagmentService(DeviceRepository deviceRepository) {
        return new DeviceManagmentServiceImpl(deviceRepository);
    }
}
