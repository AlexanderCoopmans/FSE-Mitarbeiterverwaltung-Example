package com.Mitarbeiterverwaltung.HrManagement.domain;

import java.time.LocalDate;
import java.util.Objects;

public final class EmploymentContract {
    private final Long contractId;
    private final String jobTitle;
    private final String responsibilities;
    private final Money annualSalary;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private EmploymentContract(Long contractId, String jobTitle, String responsibilities, Money annualSalary, LocalDate startDate, LocalDate endDate) {
        this.contractId = contractId;
        this.jobTitle = requireNonBlank(jobTitle, "jobTitle");
        this.responsibilities = requireNonBlank(responsibilities, "responsibilities");
        this.annualSalary = requireNonNull(annualSalary, "annualSalary");
        this.startDate = requireNonNull(startDate, "startDate");
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("endDate must not be before startDate");
        }
        if (endDate == null) {
            endDate = LocalDate.of(2999, 1, 1);
        }
        this.endDate = endDate;
    }

    public static EmploymentContract of(String jobTitle, String responsibilities, Money annualSalary, LocalDate startDate, LocalDate endDate) {
        return new EmploymentContract(null, jobTitle, responsibilities, annualSalary, startDate, endDate);
    }

    public static EmploymentContract identified(Long contractId, String jobTitle, String responsibilities, Money annualSalary, LocalDate startDate, LocalDate endDate) {
        return new EmploymentContract(contractId, jobTitle, responsibilities, annualSalary, startDate, endDate);
    }

    public EmploymentContract withId(Long newContractId) {
        return new EmploymentContract(newContractId, jobTitle, responsibilities, annualSalary, startDate, endDate);
    }

    public EmploymentContract withEndDate(LocalDate newEndDate) {
        if (newEndDate != null && newEndDate.isBefore(startDate)) {
            throw new IllegalArgumentException("endDate must not be before startDate");
        }
        return new EmploymentContract(contractId, jobTitle, responsibilities, annualSalary, startDate, newEndDate);
    }

    public boolean isActiveOn(LocalDate date) {
        if (date.isBefore(startDate)) {
            return false;
        }
        if (endDate == null) {
            return true;
        }
        return !date.isAfter(endDate); 
    }
//terminationDate ist wichtiger als endDate

    public Long getContractId() {
        return contractId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public Money getAnnualSalary() {
        return annualSalary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmploymentContract)) {
            return false;
        }
        EmploymentContract that = (EmploymentContract) o;
        return Objects.equals(contractId, that.contractId)
            && jobTitle.equals(that.jobTitle)
            && responsibilities.equals(that.responsibilities)
            && annualSalary.equals(that.annualSalary)
            && startDate.equals(that.startDate)
            && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractId, jobTitle, responsibilities, annualSalary, startDate, endDate);
    }

    @Override
    public String toString() {
        return jobTitle + " (" + responsibilities + ") from " + startDate + (endDate != null ? " to " + endDate : "");
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " must not be null");
        }
        return value;
    }
}
