package com.Mitarbeiterverwaltung.DeviceManagement.usecases;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.Device;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceAssignment;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceId;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary.DeviceManagementService;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary.DeviceRepository;

public class DeviceManagementServiceImpl implements DeviceManagementService {

    private final DeviceRepository deviceRepository;

    public DeviceManagementServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
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
                .filter(device -> device.getCurrentAssignment() != null
                        && !device.getCurrentAssignment().isReturned())
                .map(this::toReturnInformation)
                .collect(Collectors.toList());
    }

    private String toReadableInformation(Device device) {
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
            if (assignment.isReturned()) {
                builder.append(" (zurueckgegeben am ").append(assignment.getReturnedOn()).append(")");
            }
        } else {
            builder.append(" | aktuell nicht zugewiesen");
        }
        return builder.toString();
    }

    private String toReturnInformation(Device device) {
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
