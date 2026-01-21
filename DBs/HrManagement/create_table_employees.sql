-- Drop and recreate demo table for local testing
DROP TABLE IF EXISTS employee_entity;

CREATE TABLE employee_entity (
    id INT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    street VARCHAR(150) NOT NULL,
    house_number VARCHAR(20) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    iban VARCHAR(50) NOT NULL,
    bic VARCHAR(50) NOT NULL,
    account_holder VARCHAR(150) NOT NULL,
    job_title VARCHAR(150) NOT NULL,
    responsibilities VARCHAR(500) NOT NULL,
    annual_salary DECIMAL(15,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    contract_start_date DATE NOT NULL,
    contract_end_date DATE,
    termination_date DATE,
    termination_reason VARCHAR(255),
    termination_status VARCHAR(50),
    system_permissions_revoked_at DATETIME,
    devices_returned_at DATETIME
);

-- Beispiel-Daten (5 Mitarbeiter)
INSERT INTO employee_entity (
    id, first_name, last_name, street, house_number, postal_code, city, country,
    iban, bic, account_holder,
    job_title, responsibilities, annual_salary, currency,
    contract_start_date, contract_end_date,
    termination_date, termination_reason, termination_status,
    system_permissions_revoked_at, devices_returned_at
) VALUES
-- Angestellt unbefristet
(1, 'Anna', 'Schmidt', 'Hauptstrasse', '12A', '10115', 'Berlin', 'Deutschland',
 'DE44500105175407324931', 'BELADEBEXXX', 'Anna Schmidt',
 'Software Engineer', 'Backend Services', 72000.00, 'EUR',
 '2024-04-01', NULL,
 NULL, NULL, NULL,
 NULL, NULL),
-- Angestellt unbefrisstet
(2, 'Markus', 'Weber', 'Bahnhofstrasse', '5', '80331', 'Muenchen', 'Deutschland',
 'DE21500500009876543210', 'COBADEFFXXX', 'Markus Weber',
 'Product Manager', 'Feature Ownership', 85000.00, 'EUR',
 '2023-10-01', NULL,
 NULL, NULL, NULL,
 NULL, NULL),
-- Wird zum 01.05.2026 befristet eingestellt
(3, 'Sofia', 'Keller', 'Ringstrasse', '22', '50667', 'Koeln', 'Deutschland',
 'DE12500105170648489890', 'INGDDEFFXXX', 'Sofia Keller',
 'UX Designer', 'Design System', 69000.00, 'EUR',
 '2026-05-01', '2029-04-30',
 NULL, NULL, NULL,
 NULL, NULL),
-- Angestellt befristet bis 01.06.2026
(4, 'Lukas', 'Fischer', 'Seestrasse', '18', '20095', 'Hamburg', 'Deutschland',
 'DE75512108001245126199', 'HASPDEHHXXX', 'Lukas Fischer',
 'Data Engineer', 'Data Pipelines', 93000.00, 'EUR',
 '2023-06-01', '2026-06-01',
 NULL, NULL, NULL,
 NULL, NULL),
-- Vertrag ausgelaufen nicht mehr angestellt
(5, 'Mara', 'Nguyen', 'Eschenweg', '14', '60486', 'Frankfurt', 'Deutschland',
 'DE89370400440532013000', 'COBADEFFXXX', 'Mara Nguyen',
 'HR Specialist', 'Employee Relations', 62000.00, 'EUR',
 '2022-01-15', '2024-11-30',
 '2024-11-30', 'Befristung abgelaufen', 'COMPLETED',
 '2024-11-15 09:00:00', '2024-11-18 17:20:00');
