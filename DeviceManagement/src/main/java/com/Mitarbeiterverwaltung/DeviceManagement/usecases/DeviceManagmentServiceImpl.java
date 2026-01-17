package com.Mitarbeiterverwaltung.DeviceManagement.usecases;

import java.util.Optional;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.Device;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceAssignment;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceId;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary.DeviceManagmentService;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary.DeviceRepository;

public class DeviceManagmentServiceImpl implements DeviceManagmentService {

    private final DeviceRepository deviceRepository;

    public DeviceManagmentServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public String deviceInformation(int id) {
        Optional<Device> device = deviceRepository.findDeviceById(new DeviceId(id));
        return device.map(this::toReadableInformation).orElse("Device nicht gefunden");
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
}
