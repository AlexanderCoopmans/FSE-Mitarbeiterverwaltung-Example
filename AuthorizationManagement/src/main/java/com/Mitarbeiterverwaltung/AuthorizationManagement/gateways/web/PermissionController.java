package com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.web;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.ApplicationSystem;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.EmployeeReference;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.PermissionId;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.Role;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.SystemPermission;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.ValidityPeriod;
import com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.web.dto.PermissionCreateRequest;
import com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.web.dto.PermissionUpdateRequest;
import com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.web.dto.TerminationRequest;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.primary.PermissionManagementService;

@RestController
public class PermissionController {

    private final PermissionManagementService permissionManagementService;

    public PermissionController(PermissionManagementService permissionManagementService) {
        this.permissionManagementService = permissionManagementService;
    }

    @GetMapping("/permissions")
    @Operation(summary = "Berechtigungshistorie abrufen", description = "Liefert eine Liste aller aktuellen und vergangenen Berechtigungen eines Mitarbeiters anhand der Mitarbeiter-ID.")
    public ResponseEntity<List<String>> permissionsByEmployee(
            @RequestParam(name = "employeeId", required = true) Integer employeeId) {
        if (employeeId == null || employeeId <= 0) {
            return ResponseEntity.badRequest().body(List.of("employeeId is required"));
        }
        List<String> permissions = permissionManagementService.getPermissionHistory(employeeId).stream()
                .map(permissionManagementService::toReadableInformation)
                .collect(Collectors.toList());
        return ResponseEntity.ok(permissions);
    }

    @PostMapping("/permissions")
    @Operation(summary = "Neue Berechtigung erteilen", description = "Legt einen neuen Berechtigungseintrag fuer ein Anwendungssystem und eine Rolle an.")
    public ResponseEntity<String> grantPermission(@RequestBody PermissionCreateRequest request) {
        if (request == null || request.getEmployeeId() <= 0 || isBlank(request.getSystem())
                || isBlank(request.getRole())
                || request.getValidFrom() == null || request.getValidTo() == null) {
            return ResponseEntity.badRequest()
                    .body("Missing or invalid fields: employeeId, system, role, validFrom, validTo");
        }

        ValidityPeriod validityPeriod;
        try {
            validityPeriod = new ValidityPeriod(request.getValidFrom(), request.getValidTo());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        ApplicationSystem system = new ApplicationSystem(request.getSystem());
        Role role = new Role(request.getRole());
        EmployeeReference employee = new EmployeeReference(request.getEmployeeId());

        Optional<SystemPermission> created = permissionManagementService.grantPermission(employee, system, role,
                validityPeriod);
        return created.map(permissionManagementService::toReadableInformation)
                .map(body -> ResponseEntity.status(HttpStatus.CREATED).body(body))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Permission could not be created"));
    }

    @PutMapping("/permissions/{id}")
    @Operation(summary = "Berechtigung bearbeiten", description = "Aktualisiert eine bestehende Berechtigung, z.B. zur Verlaengerung des Zeitraums oder Aenderung der Rolle.")
    public ResponseEntity<String> updatePermission(@PathVariable("id") String id,
            @RequestBody PermissionUpdateRequest request) {
        PermissionId permissionId;
        try {
            permissionId = PermissionId.of(UUID.fromString(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Invalid permission id");
        }

        Role newRole = null;
        if (request != null && !isBlank(request.getRole())) {
            newRole = new Role(request.getRole());
        }

        Optional<SystemPermission> updated;
        try {
            updated = permissionManagementService.updatePermission(permissionId, newRole,
                    request != null ? request.getValidTo() : null);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return updated.map(permissionManagementService::toReadableInformation)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Permission not found"));
    }

    @DeleteMapping("/permissions/{id}")
    @Operation(summary = "Berechtigung entziehen", description = "Entfernt eine Berechtigung oder markiert sie sofort als ungueltig.")
    public ResponseEntity<String> revokePermission(@PathVariable("id") String id) {
        PermissionId permissionId;
        try {
            permissionId = PermissionId.of(UUID.fromString(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Invalid permission id");
        }

        boolean revoked = permissionManagementService.revokePermission(permissionId);
        if (!revoked) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Permission not found");
        }
        return ResponseEntity.ok("Permission revoked");
    }

    @PostMapping("/permissions/actions/terminate")
    @Operation(summary = "Alle Berechtigungen beenden (Kuendigung)", description = "Setzt das Enddatum aller aktiven Berechtigungen auf den angegebenen Stichtag. Wird vom HR-Service bei Vertragsende aufgerufen.")
    public ResponseEntity<String> terminatePermissions(@RequestBody TerminationRequest request) {
        if (request == null || request.getEmployeeId() <= 0 || request.getTerminationDate() == null) {
            return ResponseEntity.badRequest().body("Missing or invalid fields: employeeId, terminationDate");
        }

        permissionManagementService.terminateEmployeePermissions(request.getEmployeeId(), request.getTerminationDate());
        return ResponseEntity.ok("Permissions aligned to termination date");
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
