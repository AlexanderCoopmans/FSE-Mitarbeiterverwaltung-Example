package com.Mitarbeiterverwaltung.DeviceManagement.gateways.web;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary.DeviceManagementService;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceType;
import com.Mitarbeiterverwaltung.DeviceManagement.gateways.web.dto.AssignRequest;
import com.Mitarbeiterverwaltung.DeviceManagement.gateways.web.dto.DeviceCreateRequest;
import com.Mitarbeiterverwaltung.DeviceManagement.gateways.web.dto.DeviceUpdateRequest;
import com.Mitarbeiterverwaltung.DeviceManagement.gateways.web.dto.ReturnRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class DeviceManagementController {

    private final DeviceManagementService deviceManagmentService;

    public DeviceManagementController(DeviceManagementService deviceManagmentService) {
        this.deviceManagmentService = deviceManagmentService;
    }

    @Operation(summary = "Liste aller Devices", description = "Gibt die Inventarliste der Devices zurueck.")
    @GetMapping("/devices")
    public ResponseEntity<List<String>> listDevices() {
        List<String> devices = deviceManagmentService.listDevices().stream()
                .map(deviceManagmentService::toReadableInformation)
                .collect(Collectors.toList());
        return ResponseEntity.ok(devices);
    }

    @Operation(summary = "Neues Device anlegen", description = "Erstellt ein neues Device. Pflichtfelder: type, manufacturer, model")
    @PostMapping("/devices")
    public ResponseEntity<String> createDevice(@RequestBody DeviceCreateRequest request) {
        DeviceType type = parseDeviceType(request.getType());
        if (type == null || isBlank(request.getManufacturer()) || isBlank(request.getModel())) {
            return ResponseEntity.badRequest()
                    .body("Pflichtfelder fehlen oder sind ungueltig: type, manufacturer, model");
        }

        Optional<String> created = deviceManagmentService.createDevice(type, request.getManufacturer(),
                request.getModel()).map(deviceManagmentService::toReadableInformation);
        return created.map(body -> ResponseEntity.status(HttpStatus.CREATED).body(body))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Device konnte nicht erstellt werden"));
    }

    @Operation(summary = "Zeigt die Informationen eines Devices an", description = "Gibt die Informationen eines Devices anhand seiner ID zurueck.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Erfolgreiche Antwort mit den Deviceinformationen"),
            @ApiResponse(responseCode = "404", description = "Device nicht gefunden")
    })
    @GetMapping("/devices/{id}")
    public ResponseEntity<String> deviceInformation(@PathVariable("id") int id) {
        String result = deviceManagmentService.deviceInformation(id);
        if ("Device nicht gefunden".equals(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Device aktualisieren", description = "Aktualisiert Device-Stammdaten ganz oder teilweise")
    @PutMapping("/devices/{id}")
    public ResponseEntity<String> updateDevice(@PathVariable("id") int id, @RequestBody DeviceUpdateRequest request) {
        DeviceType type = parseDeviceType(request.getType());
        if (request.getType() != null && !request.getType().isBlank() && type == null) {
            return ResponseEntity.badRequest().body("Ungueltiger Device-Typ");
        }
        Optional<String> updated = deviceManagmentService.updateDevice(id, type, request.getManufacturer(),
                request.getModel()).map(deviceManagmentService::toReadableInformation);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device nicht gefunden"));
    }

    @Operation(summary = "Device entfernen", description = "Loescht oder mustert ein Device aus")
    @DeleteMapping("/devices/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") int id) {
        boolean deleted = deviceManagmentService.deleteDevice(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device nicht gefunden");
        }
        return ResponseEntity.ok("Device entfernt");
    }

    @Operation(summary = "Zeigt alle Rueckgaben in einem Monat", description = "Gibt alle Device-Zuweisungen zurueck, deren Rueckgabe in dem angegebenen Monat faellig ist. Format fuer month: yyyy-MM. Standard ist der aktuelle Monat.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Erfolgreiche Antwort mit den faelligen Rueckgaben"),
            @ApiResponse(responseCode = "400", description = "Ungueltiger Monatsparameter")
    })
    @GetMapping("/assignments/dueforreturn")
    public ResponseEntity<List<String>> assignmentsDueForReturn(
            @RequestParam(value = "month", required = false) String month) {
        YearMonth targetMonth;
        try {
            targetMonth = (month == null || month.isBlank()) ? YearMonth.now() : YearMonth.parse(month);
        } catch (DateTimeParseException ex) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonList("Ungueltiges Monatsformat. Erwartet yyyy-MM."));
        }

        List<String> results = deviceManagmentService.assignmentsDueForReturn(targetMonth);
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Device zuordnen", description = "Ordnet einem Mitarbeiter ein Device fuer einen Zeitraum zu")
    @PostMapping("/assignments")
    public ResponseEntity<String> assignDevice(@RequestBody AssignRequest request) {
        if (request.getDeviceId() <= 0 || isBlank(request.getEmployeeId()) || request.getStartDate() == null) {
            return ResponseEntity.badRequest()
                    .body("Pflichtfelder fehlen oder sind ungueltig: deviceId, employeeId, startDate");
        }
        try {
            Optional<String> assignmentInfo = deviceManagmentService.assignDevice(request.getDeviceId(),
                    request.getEmployeeId(), request.getStartDate(), request.getPlannedReturnDate())
                    .map(deviceManagmentService::toReadableInformation);
            return assignmentInfo.map(info -> ResponseEntity.status(HttpStatus.CREATED).body(info))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device nicht gefunden"));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @Operation(summary = "Aktive Zuweisungen eines Mitarbeiters", description = "Listet alle aktuell ausgeliehenen Devices einer Person")
    @GetMapping("/assignments/{employeeId}")
    public ResponseEntity<List<String>> assignmentsByEmployee(@PathVariable("employeeId") String employeeId) {
        List<String> devices = deviceManagmentService.findAssignmentsByEmployee(employeeId).stream()
                .map(deviceManagmentService::toReadableInformation)
                .collect(Collectors.toList());
        return ResponseEntity.ok(devices);
    }

    @Operation(summary = "Rueckgabe verbuchen", description = "Markiert eine Zuweisung als retourniert und gibt das Device frei")
    @PatchMapping("/assignments/{deviceId}/return")
    public ResponseEntity<String> returnAssignment(@PathVariable("deviceId") int deviceId,
            @RequestBody(required = false) ReturnRequest request) {
        LocalDate returnedAt = request != null ? request.getReturnedAt() : null;
        boolean success = deviceManagmentService.recordReturn(deviceId, returnedAt);
        if (!success) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Zuweisung nicht gefunden");
        }
        return ResponseEntity.ok("Rueckgabe verbucht");
    }

    private DeviceType parseDeviceType(String rawType) {
        if (isBlank(rawType)) {
            return null;
        }
        try {
            return DeviceType.valueOf(rawType.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
