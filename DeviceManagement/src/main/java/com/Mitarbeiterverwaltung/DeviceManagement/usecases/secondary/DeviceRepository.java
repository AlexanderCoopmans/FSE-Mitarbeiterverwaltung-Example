package com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.Device;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceId;

public interface DeviceRepository {
    Optional<Device> findDeviceById(DeviceId deviceId);

    List<Device> findAllDevices();

    List<Device> findDevicesDueForReturnInMonth(YearMonth month);

    List<Device> findDevicesAssignedToEmployee(int employeeNumber);

    Optional<Device> findByAssignmentId(String assignmentId);

    Device save(Device device);

    void delete(DeviceId deviceId);
}
