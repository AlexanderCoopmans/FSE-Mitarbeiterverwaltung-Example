package com.Mitarbeiterverwaltung.AuthorizationManagement.domain;

import java.time.LocalDate;
import java.util.Objects;

public final class SystemPermission {

    private final PermissionId permissionId;
    private final EmployeeReference employeeReference;
    private final ApplicationSystem applicationSystem;
    private final Role role;
    private ValidityPeriod validityPeriod;
    private boolean revoked;

    private SystemPermission(PermissionId permissionId,
                             EmployeeReference employeeReference,
                             ApplicationSystem applicationSystem,
                             Role role,
                             ValidityPeriod validityPeriod,
                             boolean revoked) {
        this.permissionId = Objects.requireNonNull(permissionId, "permissionId");
        this.employeeReference = Objects.requireNonNull(employeeReference, "employeeReference");
        this.applicationSystem = Objects.requireNonNull(applicationSystem, "applicationSystem");
        this.role = Objects.requireNonNull(role, "role");
        this.validityPeriod = Objects.requireNonNull(validityPeriod, "validityPeriod");
        this.revoked = revoked;
    }

    public static SystemPermission grant(PermissionId permissionId,
                                         EmployeeReference employeeReference,
                                         ApplicationSystem applicationSystem,
                                         Role role,
                                         ValidityPeriod validityPeriod) {
        return new SystemPermission(permissionId, employeeReference, applicationSystem, role, validityPeriod, false);
    }

    public PermissionId getPermissionId() {
        return permissionId;
    }

    public EmployeeReference getEmployeeReference() {
        return employeeReference;
    }

    public ApplicationSystem getApplicationSystem() {
        return applicationSystem;
    }

    public Role getRole() {
        return role;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public boolean isActiveOn(LocalDate date) {
        Objects.requireNonNull(date, "date");
        return !revoked && validityPeriod.contains(date);
    }

    public void revokeEffective(LocalDate endDate) {
        Objects.requireNonNull(endDate, "endDate");
        this.validityPeriod = this.validityPeriod.shortenTo(endDate);
        this.revoked = true;
    }

    public void alignToContractEnd(LocalDate contractEndDate) {
        Objects.requireNonNull(contractEndDate, "contractEndDate");
        this.validityPeriod = this.validityPeriod.shortenTo(contractEndDate);
        if (!contractEndDate.isAfter(LocalDate.now())) {
            this.revoked = true;
        }
    }

    public boolean belongsTo(EmployeeReference reference) {
        Objects.requireNonNull(reference, "reference");
        return this.employeeReference.equals(reference);
    }

    public boolean matchesSystem(ApplicationSystem system) {
        Objects.requireNonNull(system, "system");
        return this.applicationSystem.equals(system);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SystemPermission that = (SystemPermission) o;
        return permissionId.equals(that.permissionId);
    }

    @Override
    public int hashCode() {
        return permissionId.hashCode();
    }

    @Override
    public String toString() {
        return "Permission " + permissionId + " for employee " + employeeReference + " on " + applicationSystem + " as " + role;
    }
}
