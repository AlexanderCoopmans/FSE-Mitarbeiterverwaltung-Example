package com.Mitarbeiterverwaltung.DeviceManagement.gateways.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Mitarbeiterverwaltung.DeviceManagement.gateways.db.DeviceEntityRepository;
import com.Mitarbeiterverwaltung.DeviceManagement.gateways.db.DeviceRepositoryImpl;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.DeviceManagementServiceImpl;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary.DeviceManagementService;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary.DeviceRepository;

@Configuration
public class BeanConfiguration {

    @Bean
    public DeviceRepository deviceRepository(DeviceEntityRepository deviceEntityRepository) {
        return new DeviceRepositoryImpl(deviceEntityRepository);
    }

    @Bean
    public DeviceManagementService deviceManagementService(DeviceRepository deviceRepository) {
        return new DeviceManagementServiceImpl(deviceRepository);
    }
}
