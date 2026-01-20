package com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto;

import java.time.LocalDate;

import java.math.BigDecimal;

public class EmploymentContractResponse {
    private Long id;
    private String jobTitle;
    private String responsibilities;
    private BigDecimal annualSalary;
    private String currency;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public EmploymentContractResponse(Long id, String jobTitle, String responsibilities, BigDecimal annualSalary,
            String currency, LocalDate startDate, LocalDate endDate, String status) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.responsibilities = responsibilities;
        this.annualSalary = annualSalary;
        this.currency = currency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }
}
