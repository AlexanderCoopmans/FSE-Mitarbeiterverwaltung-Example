# FSE-Mitarbeiterverwaltung-Example

Starten mit `docker compose up` im Terminal

## Swagger UI

- [HR-Management](http://localhost:8081/swagger-ui/index.html)
- [Berechtigungsmanagement](http://localhost:8082/swagger-ui/index.html)
- [Device-Management](http://localhost:8083/swagger-ui/index.html)

## Beispiele

### **Normalzustand abfragen**

- **HR-Management:** [GET](http://localhost:8081/employees) `/employees`
- **Berechtigungsmanagement:** [GET](http://localhost:8083/permissions?employeeId=1) `/permissions`
  - **Eingabe:** `employeeId=101`
- **Device-Management:** [GET](http://localhost:8083/devices) `/devices`

### **Kündigung**

- **HR-Management:** PATCH: `/employees/{id}/termination`
  - **Eingabe:** `id = 1`

### **Automatische Auswirkungen**

- **Berechtigungsmanagement:** [GET](http://localhost:8083/permissions?employeeId=1) `/permissions`
  - **Eingabe:** `employeeId = 101`
- **Device-Management:** [GET](http://localhost:8083/devices) `/devices`

### **HR-Management Monitoring**

- **HR-Management:** [GET](http://localhost:8081/employees/1/offboarding-status) `/employees/{id}/offboarding-status`
  - **Eingabe:** `id = 1`

### **Mitarbeiter Einstellen:**

- **HR-Management:** POST: `/employees`
  - **Eingabe (application/json):**
    ```json
    {
      "employeeId": 6,
      "firstName": "Joachim",
      "lastName": "Richard",
      "street": "Mensastr.",
      "houseNumber": "5",
      "postalCode": "42069",
      "city": "Bielefeld",
      "country": "Deutschland",
      "iban": "1001 1001 1001 1001",
      "bic": "DRESDEFFXXX",
      "accountHolder": "Joachim Richard",
      "jobTitle": "Projektleiter",
      "responsibilities": "Kaffe trinken, personalkoordination",
      "annualSalary": 67000,
      "currency": "Euro",
      "startDate": "2026-01-22",
      "endDate": "2999-01-01"
    }
    ```

### **Gerät Erstellen:**

- **Device-Management:** POST: `/devices`
  - **Eingabe (application/json):**

    ```json
    {
      "type": "PC",
      "manufacturer": "Lenovo",
      "model": "Thinkpad T480"
    }
    ```

### Gerät Mitarbeiter zuordnen

- **Device-Management:** POST: `/assignments`
  - **Eingabe (application/json):**
    ```json
    {
      "deviceId": 1,
      "employeeId": 2,
      "startDate": "2026-01-22",
      "plannedReturnDate": "2026-12-31"
    }
    ```

### Mitarbeiter Systemberechtigung erteilen

- **Berechtigungsmanagement:** POST: `/permissions`
  - **Eingabe (application/json):**
    ```json
    {
      "employeeId": 2,
      "system": "Jira",
      "role": "Produktmanager",
      "validFrom": "2026-01-22",
      "validTo": "2027-01-06"
    }
    ```
