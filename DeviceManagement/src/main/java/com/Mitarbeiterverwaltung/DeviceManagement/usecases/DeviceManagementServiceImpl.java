package com.Mitarbeiterverwaltung.DeviceManagement.usecases;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.Device;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceAssignment;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceId;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceType;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.EmployeeReference;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.ValidityPeriod;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.events.AllDevicesReturnedEvent;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary.DeviceManagementService;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary.AllDevicesReturnedEventPublisher;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary.DeviceRepository;

public class DeviceManagementServiceImpl implements DeviceManagementService {

    private final DeviceRepository deviceRepository;
    private AllDevicesReturnedEventPublisher allDevicesReturnedEventPublisher;

    public DeviceManagementServiceImpl(DeviceRepository deviceRepository,
            AllDevicesReturnedEventPublisher allDevicesReturnedEventPublisher) {
        this.deviceRepository = deviceRepository;
        this.allDevicesReturnedEventPublisher = allDevicesReturnedEventPublisher;
    }

    @Override
    public String deviceInformation(int id) {
        Optional<Device> device = deviceRepository.findDeviceById(new DeviceId(id));
        return device.map(this::toReadableInformation).orElse("Device nicht gefunden");
    }

    @Override
    public List<String> assignmentsDueForReturn(YearMonth month) {
        YearMonth targetMonth = month != null ? month : YearMonth.now();
        return deviceRepository.findDevicesDueForReturnInMonth(targetMonth).stream()
                .filter(device -> device.getCurrentAssignment() != null)
                .map(this::toReturnInformation)
                .collect(Collectors.toList());
    }

    @Override
    public List<Device> listDevices() {
        return deviceRepository.findAllDevices();
    }

    @Override
    public Optional<Device> createDevice(DeviceType deviceType, String manufacturer, String designation) {
        if (deviceType == null || manufacturer == null || manufacturer.isBlank() || designation == null
                || designation.isBlank()) {
            return Optional.empty();
        }
        Device device = new Device(new DeviceId(), deviceType, manufacturer.trim(), designation.trim());
        Device saved = deviceRepository.save(device);
        return Optional.ofNullable(saved);
    }

    @Override
    public Optional<Device> updateDevice(int id, DeviceType deviceType, String manufacturer, String designation) {
        Optional<Device> current = deviceRepository.findDeviceById(new DeviceId(id));
        if (current.isEmpty()) {
            return Optional.empty();
        }
        Device updated = current.get().withUpdatedDetails(deviceType,
                manufacturer != null ? manufacturer.trim() : null,
                designation != null ? designation.trim() : null);
        Device saved = deviceRepository.save(updated);
        return Optional.of(saved);
    }

    @Override
    public boolean deleteDevice(int id) {
        Optional<Device> current = deviceRepository.findDeviceById(new DeviceId(id));
        if (current.isEmpty()) {
            return false;
        }
        deviceRepository.delete(new DeviceId(id));
        return true;
    }

    @Override
    public Optional<Device> assignDevice(int deviceId, String employeeId, LocalDate startDate,
            LocalDate endDate) {
        Optional<Device> deviceOpt = deviceRepository.findDeviceById(new DeviceId(deviceId));
        if (deviceOpt.isEmpty()) {
            return Optional.empty();
        }
        Device device = deviceOpt.get();
        ValidityPeriod period = new ValidityPeriod(startDate, endDate);
        device.assignToEmployee(new EmployeeReference(employeeId), period);
        Device saved = deviceRepository.save(device);
        return Optional.ofNullable(saved);
    }

    @Override
    public List<Device> findAssignmentsByEmployee(String employeeId) {
        return deviceRepository.findDevicesAssignedToEmployee(employeeId);
    }

    @Override
    public boolean recordReturn(int deviceId, LocalDate returnDate) {
        Optional<Device> deviceOpt = deviceRepository.findDeviceById(new DeviceId(deviceId));
        if (deviceOpt.isEmpty()) {
            return false;
        }
        Device device = deviceOpt.get();
        device.recordReturn();
        deviceRepository.save(device);
        EmployeeReference employeeId = device.getCurrentAssignment() != null
                ? device.getCurrentAssignment().getEmployee()
                : null;

        if (employeeId == null) {
            return true;
        }
        List<Device> employeeAssignments = findAssignmentsByEmployee(employeeId.getEmployeeNumber());
        if (employeeAssignments.isEmpty()) {
            returnDate = returnDate != null ? returnDate : LocalDate.now();
            AllDevicesReturnedEvent event = new AllDevicesReturnedEvent(employeeId, returnDate);
            allDevicesReturnedEventPublisher.publishDomainEvent(event);
        }
        return true;
    }

    @Override
    public String toReadableInformation(Device device) {
        StringBuilder builder = new StringBuilder();
        builder.append("Device ").append(device.getDeviceId().getId())
                .append(": ").append(device.getDeviceType())
                .append(" - ").append(device.getManufacturer())
                .append(" ").append(device.getDesignation());

        DeviceAssignment assignment = device.getCurrentAssignment();
        if (assignment != null) {
            builder.append(" | Zugewiesen an Mitarbeiter ")
                    .append(assignment.getEmployee().getEmployeeNumber())
                    .append(" von ").append(assignment.getValidityPeriod().getStartDate())
                    .append(" bis ").append(assignment.getValidityPeriod().getEndDate());
        } else {
            builder.append(" | aktuell nicht zugewiesen");
        }
        return builder.toString();
    }

    @Override
    public String toReturnInformation(Device device) {
        DeviceAssignment assignment = device.getCurrentAssignment();
        StringBuilder builder = new StringBuilder();
        builder.append("Device ").append(device.getDeviceId().getId());
        if (assignment != null) {
            builder.append(" Rueckgabe faellig bis ")
                    .append(assignment.getValidityPeriod().getEndDate())
                    .append(" | Mitarbeiter ")
                    .append(assignment.getEmployee().getEmployeeNumber());
        } else {
            builder.append(" | keine aktuelle Zuweisung");
        }
        return builder.toString();
    }
}
