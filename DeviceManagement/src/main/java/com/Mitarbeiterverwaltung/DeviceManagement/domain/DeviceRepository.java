package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository {
    Device save(Device device);

    Optional<Device> findByDeviceNumber(DeviceNumber deviceNumber);

    List<Device> findByEmployee(EmployeeReference employeeReference);

    List<Device> findAll();
}
