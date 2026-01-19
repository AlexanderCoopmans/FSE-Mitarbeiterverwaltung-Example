package com.Mitarbeiterverwaltung.AuthorizationManagement.gateways.db;

import java.time.LocalDate;
import java.util.UUID;

import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.ApplicationSystem;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.EmployeeReference;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.PermissionId;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.Role;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.SystemPermission;
import com.Mitarbeiterverwaltung.AuthorizationManagement.domain.ValidityPeriod;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PermissionEntity {

    @Id
    private String id;
    private int employeeNumber;
    private String systemName;
    private String roleName;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private boolean revoked;

    public PermissionEntity() {
    }

    public PermissionEntity(String id,
            int employeeNumber,
            String systemName,
            String roleName,
            LocalDate validFrom,
            LocalDate validUntil,
            boolean revoked) {
        this.id = id;
        this.employeeNumber = employeeNumber;
        this.systemName = systemName;
        this.roleName = roleName;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.revoked = revoked;
    }

    public SystemPermission toDomain() {
        PermissionId permissionId = PermissionId.of(UUID.fromString(this.id));
        EmployeeReference employee = new EmployeeReference(this.employeeNumber);
        ApplicationSystem system = new ApplicationSystem(this.systemName);
        Role role = new Role(this.roleName);
        ValidityPeriod validityPeriod = new ValidityPeriod(this.validFrom, this.validUntil);
        return SystemPermission.restore(permissionId, employee, system, role, validityPeriod, this.revoked);
    }

    public static PermissionEntity fromDomain(SystemPermission permission) {
        return new PermissionEntity(
                permission.getPermissionId().getValue().toString(),
                permission.getEmployeeReference().getEmployeeNumber(),
                permission.getApplicationSystem().getSystemName(),
                permission.getRole().getRoleName(),
                permission.getValidityPeriod().getValidFrom(),
                permission.getValidityPeriod().getValidUntil(),
                permission.isRevoked());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}
