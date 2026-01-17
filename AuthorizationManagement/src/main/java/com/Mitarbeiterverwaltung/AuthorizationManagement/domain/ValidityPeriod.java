package com.Mitarbeiterverwaltung.AuthorizationManagement.domain;

import java.time.LocalDate;
import java.util.Objects;

public final class ValidityPeriod {

    private final LocalDate validFrom;
    private final LocalDate validUntil;

    public ValidityPeriod(LocalDate validFrom, LocalDate validUntil) {
        this.validFrom = Objects.requireNonNull(validFrom, "validFrom");
        this.validUntil = Objects.requireNonNull(validUntil, "validUntil");
        if (validUntil.isBefore(validFrom)) {
            throw new IllegalArgumentException("validUntil must not be before validFrom");
        }
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public boolean contains(LocalDate date) {
        Objects.requireNonNull(date, "date");
        return !date.isBefore(validFrom) && !date.isAfter(validUntil);
    }

    public boolean endsOnOrBefore(LocalDate date) {
        Objects.requireNonNull(date, "date");
        return !validUntil.isAfter(date);
    }

    public ValidityPeriod shortenTo(LocalDate newValidUntil) {
        Objects.requireNonNull(newValidUntil, "newValidUntil");
        if (newValidUntil.isBefore(validFrom)) {
            throw new IllegalArgumentException("newValidUntil must not be before validFrom");
        }
        if (newValidUntil.isAfter(validUntil)) {
            return this; // ignore attempts to extend beyond original end
        }
        return new ValidityPeriod(validFrom, newValidUntil);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValidityPeriod that = (ValidityPeriod) o;
        return validFrom.equals(that.validFrom) && validUntil.equals(that.validUntil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validFrom, validUntil);
    }

    @Override
    public String toString() {
        return validFrom + " to " + validUntil;
    }
}
