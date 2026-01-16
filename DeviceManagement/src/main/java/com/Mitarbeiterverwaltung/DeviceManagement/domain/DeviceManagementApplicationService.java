package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeviceManagementApplicationService {
    private final DeviceRepository deviceRepository;

    public DeviceManagementApplicationService(DeviceRepository deviceRepository) {
        this.deviceRepository = Objects.requireNonNull(deviceRepository, "deviceRepository must not be null");
    }

    public Device registerDevice(DeviceNumber number, DeviceType type, String manufacturer, String designation) {
        Device device = Device.of(number, type, manufacturer, designation);
        return deviceRepository.save(device);
    }

    public DeviceAssignment assignDevice(DeviceNumber number, EmployeeReference employee, ValidityPeriod period) {
        Device device = deviceRepository.findByDeviceNumber(number)
                .orElseThrow(() -> new IllegalArgumentException("Device not found: " + number));
        DeviceAssignment assignment = device.assignToEmployee(employee, period);
        deviceRepository.save(device);
        return assignment;
    }

    public void recordReturn(DeviceNumber number, AssignmentId assignmentId, LocalDate returnDate) {
        Device device = deviceRepository.findByDeviceNumber(number)
                .orElseThrow(() -> new IllegalArgumentException("Device not found: " + number));
        device.recordReturn(assignmentId, returnDate);
        deviceRepository.save(device);
    }

    public List<DeviceAssignment> assignmentsForDevice(DeviceNumber number) {
        Device device = deviceRepository.findByDeviceNumber(number)
                .orElseThrow(() -> new IllegalArgumentException("Device not found: " + number));
        return device.getAssignments();
    }

    public List<Device> devicesForEmployee(EmployeeReference employee) {
        return deviceRepository.findByEmployee(employee);
    }

    public List<Device> devicesWithReturnsDue(YearMonth month) {
        List<Device> result = new ArrayList<>();
        for (Device device : deviceRepository.findAll()) {
            if (!device.assignmentsDueInMonth(month).isEmpty()) {
                result.add(device);
            }
        }
        return result;
    }

    public void enforceReturnDeadlineForEmployee(EmployeeReference employee, LocalDate deadline) {
        List<Device> devices = deviceRepository.findByEmployee(employee);
        for (Device device : devices) {
            device.enforceReturnBy(employee, deadline);
            deviceRepository.save(device);
        }
    }
}
