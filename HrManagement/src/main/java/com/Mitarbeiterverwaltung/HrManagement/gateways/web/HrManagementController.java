package com.Mitarbeiterverwaltung.HrManagement.gateways.web;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Mitarbeiterverwaltung.HrManagement.domain.Address;
import com.Mitarbeiterverwaltung.HrManagement.domain.BankAccount;
import com.Mitarbeiterverwaltung.HrManagement.domain.Employee;
import com.Mitarbeiterverwaltung.HrManagement.domain.EmployeeNumber;
import com.Mitarbeiterverwaltung.HrManagement.domain.EmploymentContract;
import com.Mitarbeiterverwaltung.HrManagement.domain.Money;
import com.Mitarbeiterverwaltung.HrManagement.domain.Name;
import com.Mitarbeiterverwaltung.HrManagement.domain.TerminationProcessInformation;
import com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto.AddressResponse;
import com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto.BankAccountResponse;
import com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto.ContractCreateRequest;
import com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto.EmployeeCreateRequest;
import com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto.EmployeeResponse;
import com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto.EmploymentContractResponse;
import com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto.OffboardingStatusResponse;
import com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto.TerminateContractRequest;
import com.Mitarbeiterverwaltung.HrManagement.usecases.primary.HrManagementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class HrManagementController {

    private final HrManagementService hrManagementService;

    public HrManagementController(HrManagementService hrManagementService) {
        this.hrManagementService = hrManagementService;
    }

    @Operation(summary = "Neuen Mitarbeiter anlegen", description = "Legt einen Mitarbeiter inklusive initialem Arbeitsvertrag an")
    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeCreateRequest request) {
        if (!isValidCreateRequest(request)) {
            return ResponseEntity.badRequest().body("Pflichtfelder fehlen oder sind ungueltig");
        }
        try {
            Employee employee = Employee.hire(
                    EmployeeNumber.of(request.getEmployeeId()),
                    Name.of(request.getFirstName(), request.getLastName()),
                    Address.of(request.getStreet(), request.getHouseNumber(), request.getPostalCode(),
                            request.getCity(), request.getCountry()),
                    BankAccount.of(request.getIban(), request.getBic(), request.getAccountHolder()),
                    EmploymentContract.of(request.getJobTitle(), request.getResponsibilities(),
                            Money.of(request.getAnnualSalary(), request.getCurrency()),
                            request.getStartDate(), request.getEndDate()));
            Optional<Employee> saved = hrManagementService.createEmployee(employee);
            if (saved.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(toEmployeeResponse(saved.get()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Mitarbeiter konnte nicht angelegt werden");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Mitarbeiter anzeigen", description = "Gibt die Personalakte inklusive aktuellem Vertrag zurueck")
    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable("id") int id) {
        Optional<Employee> employee = hrManagementService.getEmployee(id);
        return employee.<ResponseEntity<?>>map(value -> ResponseEntity.ok(toEmployeeResponse(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mitarbeiter nicht gefunden"));
    }

    @Operation(summary = "Aktive Mitarbeiter", description = "Listet alle Mitarbeiter, die zu einem Datum aktiv sind")
    @GetMapping(value = "/employees", params = { "activeAt" })
    public ResponseEntity<?> activeEmployees(@RequestParam(value = "activeAt", required = false) String activeAt) {
        LocalDate date;
        try {
            date = (activeAt == null || activeAt.isBlank()) ? LocalDate.now() : LocalDate.parse(activeAt);
        } catch (DateTimeParseException ex) {
            return ResponseEntity.badRequest().body("Ungueltiges Datumsformat. Erwartet YYYY-MM-DD");
        }
        List<EmployeeResponse> employees = hrManagementService.getEmployeesActiveAt(date).stream()
                .map(this::toEmployeeResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Neuen Vertrag hinzufuegen", description = "Ergaenzt einen bestehenden Mitarbeiter um einen neuen Vertrag")
    @PostMapping("/employees/{id}/contracts")
    public ResponseEntity<?> addContract(@PathVariable("id") int employeeId,
            @RequestBody ContractCreateRequest request) {
        if (!isValidContractRequest(request)) {
            return ResponseEntity.badRequest().body("Pflichtfelder fehlen oder sind ungueltig");
        }
        try {
            EmploymentContract contract = EmploymentContract.of(request.getJobTitle(), request.getResponsibilities(),
                    Money.of(request.getAnnualSalary(), request.getCurrency()), request.getStartDate(),
                    request.getEndDate());
            Optional<EmploymentContract> saved = hrManagementService.addEmploymentContract(employeeId, contract);
            return saved.<ResponseEntity<?>>map(value -> ResponseEntity.status(HttpStatus.CREATED).body(toContractResponse(value)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mitarbeiter nicht gefunden"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

        @Operation(summary = "Vertrag kuendigen", description = "Markiert den Vertrag eines Mitarbeiters als gekuendigt und startet Offboarding")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kuendigung verbucht"),
            @ApiResponse(responseCode = "404", description = "Mitarbeiter nicht gefunden"),
            @ApiResponse(responseCode = "409", description = "Kuendigung ungueltig") })
        @PatchMapping("/employees/{id}/termination")
        public ResponseEntity<?> terminateContract(@PathVariable("id") int employeeId,
            @RequestBody TerminateContractRequest request) {
        if (request == null || request.getTerminationDate() == null || isBlank(request.getReason())) {
            return ResponseEntity.badRequest().body("terminationDate und reason sind Pflichtfelder");
        }
        try {
            Optional<Employee> employee = hrManagementService.terminateContract(employeeId, request.getTerminationDate(),
                    request.getReason());
            return employee.<ResponseEntity<?>>map(value -> ResponseEntity.ok(toEmployeeResponse(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mitarbeiter nicht gefunden"));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Offboarding-Status", description = "Gibt den Status des Offboardings (Berechtigungen & Devices) zurueck")
    @GetMapping("/employees/{id}/offboarding-status")
    public ResponseEntity<?> offboardingStatus(@PathVariable("id") int employeeId) {
        Optional<TerminationProcessInformation> status = hrManagementService.getOffboardingStatus(employeeId);
        if (status.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offboarding wurde nicht gestartet");
        }
        OffboardingStatusResponse response = toOffboardingResponse(status.get());
        return ResponseEntity.ok(response);
    }

    private boolean isValidCreateRequest(EmployeeCreateRequest request) {
        return request != null && request.getEmployeeId() != null && request.getEmployeeId() > 0
                && !isBlank(request.getFirstName()) && !isBlank(request.getLastName()) && !isBlank(request.getStreet())
                && !isBlank(request.getHouseNumber()) && !isBlank(request.getPostalCode()) && !isBlank(request.getCity())
                && !isBlank(request.getCountry()) && !isBlank(request.getIban()) && !isBlank(request.getBic())
                && !isBlank(request.getAccountHolder()) && !isBlank(request.getJobTitle())
                && !isBlank(request.getResponsibilities()) && request.getAnnualSalary() != null
                && request.getAnnualSalary().signum() > 0 && !isBlank(request.getCurrency())
                && request.getStartDate() != null;
    }

    private boolean isValidContractRequest(ContractCreateRequest request) {
        return request != null && !isBlank(request.getJobTitle()) && !isBlank(request.getResponsibilities())
                && request.getAnnualSalary() != null && request.getAnnualSalary().signum() > 0
                && !isBlank(request.getCurrency()) && request.getStartDate() != null;
    }

    private EmployeeResponse toEmployeeResponse(Employee employee) {
        AddressResponse address = new AddressResponse(employee.getAddress().getStreet(),
                employee.getAddress().getHouseNumber(), employee.getAddress().getPostalCode(),
                employee.getAddress().getCity(), employee.getAddress().getCountry());
        BankAccountResponse bankAccount = new BankAccountResponse(employee.getBankAccount().getIban(),
                employee.getBankAccount().getBic(), employee.getBankAccount().getAccountHolder());
        EmploymentContractResponse contract = toContractResponse(employee.getEmploymentContract());
        return new EmployeeResponse(employee.getEmployeeNumber().getValue(), employee.getName().getFirstName(),
                employee.getName().getLastName(), address, bankAccount, contract);
    }

    private EmploymentContractResponse toContractResponse(EmploymentContract contract) {
        String status = resolveContractStatus(contract.getEndDate());
        return new EmploymentContractResponse(contract.getJobTitle(), contract.getResponsibilities(),
            contract.getAnnualSalary().getAmount(), contract.getAnnualSalary().getCurrency(),
            contract.getStartDate(), contract.getEndDate(), status);
    }

    private OffboardingStatusResponse toOffboardingResponse(TerminationProcessInformation info) {
        return new OffboardingStatusResponse(info.getTerminationDate(), info.getTerminationReason(),
                info.getStatus().name(), info.isStatusSystemPermissionsRevoked(),
                info.getLastSystemPermissionRevokedAt(), info.isStatusDevicesReturned(),
                info.getLastDevicesReturnedAt());
    }

    private String resolveContractStatus(LocalDate endDate) {
        if (endDate == null) {
            return "ACTIVE";
        }
        return endDate.isBefore(LocalDate.now()) ? "TERMINATED" : "ACTIVE";
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
