package com.Mitarbeiterverwaltung.DeviceManagement.domain;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

public final class ValidityPeriod {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public ValidityPeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date must not be null");
        }
        if (endDate == null) {
            endDate = LocalDate.of(2999, 1, 1);
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must not be before start date");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean contains(LocalDate date) {
        if (date == null) {
            return false;
        }
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public boolean overlaps(ValidityPeriod other) {
        Objects.requireNonNull(other, "other period must not be null");
        return !other.endDate.isBefore(startDate) && !other.startDate.isAfter(endDate);
    }

    public boolean endsInMonth(YearMonth month) {
        Objects.requireNonNull(month, "month must not be null");
        return YearMonth.from(endDate).equals(month);
    }

    public ValidityPeriod shortenTo(LocalDate newEndDate) {
        Objects.requireNonNull(newEndDate, "new end date must not be null");
        if (newEndDate.isBefore(startDate)) {
            throw new IllegalArgumentException("New end date must not be before start date");
        }
        LocalDate adjustedEnd = newEndDate.isBefore(endDate) ? newEndDate : endDate;
        return new ValidityPeriod(startDate, adjustedEnd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ValidityPeriod)) {
            return false;
        }
        ValidityPeriod that = (ValidityPeriod) o;
        return startDate.equals(that.startDate) && endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @Override
    public String toString() {
        return startDate + " to " + endDate;
    }
}
