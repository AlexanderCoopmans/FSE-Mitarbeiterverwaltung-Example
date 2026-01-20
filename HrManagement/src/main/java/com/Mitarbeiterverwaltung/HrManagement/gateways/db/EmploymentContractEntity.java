package com.Mitarbeiterverwaltung.HrManagement.gateways.db;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employment_contracts")
public class EmploymentContractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int employeeId;
    private String jobTitle;
    private String responsibilities;
    private BigDecimal annualSalary;
    private String currency;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public EmploymentContractEntity() {
    }

    public EmploymentContractEntity(Long id, int employeeId, String jobTitle, String responsibilities,
            BigDecimal annualSalary, String currency, LocalDate startDate, LocalDate endDate, String status) {
        this.id = id;
        this.employeeId = employeeId;
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

    public void setId(Long id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(BigDecimal annualSalary) {
        this.annualSalary = annualSalary;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
