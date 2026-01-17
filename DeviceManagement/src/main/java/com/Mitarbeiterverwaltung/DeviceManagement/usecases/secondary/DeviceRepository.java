package com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.Device;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceId;

import java.util.Optional;

public interface DeviceRepository {
    public Optional<Device> findDeviceById(DeviceId deviceId);

    public void save(Device device);
}
