package com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.Device;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceId;

public interface DeviceRepository {
    public Optional<Device> findDeviceById(DeviceId deviceId);

    public List<Device> findDevicesDueForReturnInMonth(YearMonth month);

    public void save(Device device);
}
