package com.Mitarbeiterverwaltung.DeviceManagement.gateways.web;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary.DeviceManagementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class DeviceManagamentController {

    private final DeviceManagementService deviceManagmentService;

    public DeviceManagamentController(DeviceManagementService deviceManagmentService) {
        this.deviceManagmentService = deviceManagmentService;
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
}
