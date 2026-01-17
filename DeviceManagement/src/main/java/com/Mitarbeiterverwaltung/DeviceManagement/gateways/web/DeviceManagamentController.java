package com.Mitarbeiterverwaltung.DeviceManagement.gateways.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary.DeviceManagmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class DeviceManagamentController {

    private final DeviceManagmentService deviceManagmentService;

    public DeviceManagamentController(DeviceManagmentService deviceManagmentService) {
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
}
