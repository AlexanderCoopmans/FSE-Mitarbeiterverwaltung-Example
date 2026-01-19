package com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.secondary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.PermissionId;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.SystemPermission;

public interface PermissionRepository {

    Optional<SystemPermission> findById(PermissionId permissionId);

    List<SystemPermission> findByEmployee(int employeeId);

    List<SystemPermission> findActivePermissionsByEmployee(int employeeId, LocalDate asOfDate);

    SystemPermission save(SystemPermission permission);

    void delete(PermissionId permissionId);
}
