package com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.db;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.PermissionId;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.SystemPermission;
import com.Mitarbeiterverwaltung.AuthorizationManagement.usecases.secondary.PermissionRepository;

public class PermissionRepositoryImpl implements PermissionRepository {

    private final PermissionEntityRepository permissionEntityRepository;

    public PermissionRepositoryImpl(PermissionEntityRepository permissionEntityRepository) {
        this.permissionEntityRepository = permissionEntityRepository;
    }

    @Override
    public Optional<SystemPermission> findById(PermissionId permissionId) {
        return permissionEntityRepository.findById(permissionId.getValue().toString())
                .map(PermissionEntity::toDomain);
    }

    @Override
    public List<SystemPermission> findByEmployee(int employeeId) {
        return permissionEntityRepository.findByEmployeeNumber(employeeId).stream()
                .map(PermissionEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SystemPermission> findActivePermissionsByEmployee(int employeeId, LocalDate asOfDate) {
        return permissionEntityRepository.findActiveByEmployeeNumber(employeeId, asOfDate).stream()
                .map(PermissionEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public SystemPermission save(SystemPermission permission) {
        PermissionEntity entity = PermissionEntity.fromDomain(permission);
        PermissionEntity saved = permissionEntityRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public void delete(PermissionId permissionId) {
        permissionEntityRepository.deleteById(permissionId.getValue().toString());
    }
}
