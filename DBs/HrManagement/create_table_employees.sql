-- Drop and recreate demo table for local testing
DROP TABLE IF EXISTS employee_entity;

CREATE TABLE employee_entity (
    id INT PRIMARY KEY,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    street VARCHAR(150) NOT NULL,
    houseNumber VARCHAR(20) NOT NULL,
    postalCode VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    iban VARCHAR(50) NOT NULL,
    bic VARCHAR(50) NOT NULL,
    accountHolder VARCHAR(150) NOT NULL,
    jobTitle VARCHAR(150) NOT NULL,
    responsibilities VARCHAR(500) NOT NULL,
    annualSalary DECIMAL(15,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    contractStartDate DATE NOT NULL,
    contractEndDate DATE,
    terminationDate DATE,
    terminationReason VARCHAR(255),
    terminationStatus VARCHAR(50),
    systemPermissionsRevokedAt DATETIME,
    devicesReturnedAt DATETIME
);

-- Beispiel-Daten (5 Mitarbeiter)
INSERT INTO employee_entity (
    id, firstName, lastName, street, houseNumber, postalCode, city, country,
    iban, bic, accountHolder,
    jobTitle, responsibilities, annualSalary, currency,
    contractStartDate, contractEndDate,
    terminationDate, terminationReason, terminationStatus,
    systemPermissionsRevokedAt, devicesReturnedAt
) VALUES
(1, 'Anna', 'Schmidt', 'Hauptstrasse', '12A', '10115', 'Berlin', 'Deutschland',
 'DE44500105175407324931', 'BELADEBEXXX', 'Anna Schmidt',
 'Software Engineer', 'Backend Services', 72000.00, 'EUR',
 '2024-04-01', NULL,
 NULL, NULL, NULL,
 NULL, NULL),

(2, 'Markus', 'Weber', 'Bahnhofstrasse', '5', '80331', 'Muenchen', 'Deutschland',
 'DE21500500009876543210', 'COBADEFFXXX', 'Markus Weber',
 'Product Manager', 'Feature Ownership', 85000.00, 'EUR',
 '2023-10-01', NULL,
 NULL, NULL, NULL,
 NULL, NULL),

(3, 'Sofia', 'Keller', 'Ringstrasse', '22', '50667', 'Koeln', 'Deutschland',
 'DE12500105170648489890', 'INGDDEFFXXX', 'Sofia Keller',
 'UX Designer', 'Design System', 69000.00, 'EUR',
 '2026-05-01', '2029-04-30',
 NULL, NULL, NULL,
 NULL, NULL),

(4, 'Lukas', 'Fischer', 'Seestrasse', '18', '20095', 'Hamburg', 'Deutschland',
 'DE75512108001245126199', 'HASPDEHHXXX', 'Lukas Fischer',
 'Data Engineer', 'Data Pipelines', 93000.00, 'EUR',
 '2023-06-01', '2026-06-01',
 '2026-02-28', 'Teamumbau', 'IN_PROGRESS',
 NULL, NULL),

(5, 'Mara', 'Nguyen', 'Eschenweg', '14', '60486', 'Frankfurt', 'Deutschland',
 'DE89370400440532013000', 'COBADEFFXXX', 'Mara Nguyen',
 'HR Specialist', 'Employee Relations', 62000.00, 'EUR',
 '2022-01-15', '2024-11-30',
 '2024-11-30', 'Befristung abgelaufen', 'COMPLETED',
 '2024-11-15 09:00:00', '2024-11-18 17:20:00');
