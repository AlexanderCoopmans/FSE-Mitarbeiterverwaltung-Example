package com.Mitarbeiterverwaltung.AuthorizationManagement.usecases;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.ApplicationSystem;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.EmployeeReference;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.PermissionId;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.Role;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.SystemPermission;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.ValidityPeriod;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.primary.OffboardingStatus;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.primary.PermissionManagementService;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.secondary.OffboardingStatusEventPublisher;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.secondary.PermissionRepository;

public class PermissionManagementServiceImpl implements PermissionManagementService {

    private final PermissionRepository permissionRepository;
    private final OffboardingStatusEventPublisher offboardingStatusEventPublisher;

    public PermissionManagementServiceImpl(PermissionRepository permissionRepository,
            OffboardingStatusEventPublisher offboardingStatusEventPublisher) {
        this.permissionRepository = permissionRepository;
        this.offboardingStatusEventPublisher = offboardingStatusEventPublisher;
    }

    @Override
    public List<SystemPermission> getPermissionHistory(int employeeId) {
        return permissionRepository.findByEmployee(employeeId);
    }

    @Override
    public Optional<SystemPermission> grantPermission(EmployeeReference employee,
            ApplicationSystem system,
            Role role,
            ValidityPeriod validityPeriod) {
        if (employee == null || system == null || role == null || validityPeriod == null) {
            return Optional.empty();
        }
        SystemPermission permission = SystemPermission.grant(PermissionId.newId(), employee, system, role,
                validityPeriod);
        SystemPermission saved = permissionRepository.save(permission);
        return Optional.ofNullable(saved);
    }

    @Override
    public Optional<SystemPermission> updatePermission(PermissionId permissionId, Role newRole, LocalDate newValidUntil) {
        return permissionRepository.findById(permissionId)
                .map(permission -> {
                    permission.update(newRole, newValidUntil);
                    return permissionRepository.save(permission);
                });
    }

    @Override
    public boolean revokePermission(PermissionId permissionId) {
        Optional<SystemPermission> permissionOpt = permissionRepository.findById(permissionId);
        if (permissionOpt.isEmpty()) {
            return false;
        }
        SystemPermission permission = permissionOpt.get();
        permission.revokeEffective(LocalDate.now());
        permissionRepository.save(permission);
        int employeeId = permission.getEmployeeReference().getEmployeeNumber();
        offboardingStatusEventPublisher.publishOffboardingStatus(employeeId, checkOffboardingStatus(employeeId));
        return true;
    }

    @Override
    public void terminateEmployeePermissions(int employeeId, LocalDate terminationDate) {
        LocalDate endDate = terminationDate != null ? terminationDate : LocalDate.now();
        List<SystemPermission> activePermissions = permissionRepository.findActivePermissionsByEmployee(employeeId,
                endDate);
        for (SystemPermission permission : activePermissions) {
            permission.alignToContractEnd(endDate);
            permissionRepository.save(permission);
        }
        offboardingStatusEventPublisher.publishOffboardingStatus(employeeId, checkOffboardingStatus(employeeId));
    }

    @Override
    public OffboardingStatus checkOffboardingStatus(int employeeId) {
        List<SystemPermission> permissions = permissionRepository.findByEmployee(employeeId);
        boolean anyActive = permissions.stream().anyMatch(p -> p.isActiveOn(LocalDate.now()));
        LocalDate lastRevokedAt = permissions.stream()
                .map(p -> p.getValidityPeriod().getValidUntil())
                .max(Comparator.naturalOrder())
                .orElse(null);
        return new OffboardingStatus(!anyActive, lastRevokedAt);
    }

    @Override
    public String toReadableInformation(SystemPermission permission) {
        StringBuilder builder = new StringBuilder();
        builder.append("Permission ")
                .append(permission.getPermissionId().getValue())
                .append(" | Employee ")
                .append(permission.getEmployeeReference().getEmployeeNumber())
                .append(" | System ")
                .append(permission.getApplicationSystem().getSystemName())
                .append(" | Role ")
                .append(permission.getRole().getRoleName())
                .append(" | Valid ")
                .append(permission.getValidityPeriod().getValidFrom())
                .append(" to ")
                .append(permission.getValidityPeriod().getValidUntil());

        if (permission.isRevoked()) {
            builder.append(" | revoked");
        }
        return builder.toString();
    }
}
