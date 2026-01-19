package com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.Device;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceType;

public interface DeviceManagementService {

    String deviceInformation(int id);

    List<String> assignmentsDueForReturn(YearMonth month);

    List<Device> listDevices();

    Optional<Device> createDevice(DeviceType deviceType, String manufacturer, String designation);

    Optional<Device> updateDevice(int id, DeviceType deviceType, String manufacturer, String designation);

    boolean deleteDevice(int id);

    Optional<Device> assignDevice(int deviceId, int employeeId, LocalDate startDate, LocalDate endDate);

    List<Device> findAssignmentsByEmployee(int employeeId);

    boolean recordReturn(int deviceId, LocalDate returnDate);

    String toReadableInformation(Device device);

    String toReturnInformation(Device device);

    void handleEmploymentTermination(int employeeId, LocalDate terminationDate);
}
