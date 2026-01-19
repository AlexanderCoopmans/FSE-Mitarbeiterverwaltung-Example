package com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.primary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.ApplicationSystem;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.EmployeeReference;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.PermissionId;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.Role;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.SystemPermission;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.ValidityPeriod;

public interface PermissionManagementService {

    List<SystemPermission> getPermissionHistory(int employeeId);

    Optional<SystemPermission> grantPermission(EmployeeReference employee,
            ApplicationSystem system,
            Role role,
            ValidityPeriod validityPeriod);

    Optional<SystemPermission> updatePermission(PermissionId permissionId,
            Role newRole,
            LocalDate newValidUntil);

    boolean revokePermission(PermissionId permissionId);

    void terminateEmployeePermissions(int employeeId, LocalDate terminationDate);

    OffboardingStatus checkOffboardingStatus(int employeeId);

    String toReadableInformation(SystemPermission permission);
}
