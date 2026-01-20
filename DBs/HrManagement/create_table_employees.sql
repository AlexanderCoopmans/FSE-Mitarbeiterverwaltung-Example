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
  terminationDate DATE,
  terminationReason VARCHAR(255),
  terminationStatus VARCHAR(50),
  systemPermissionsRevokedAt DATETIME,
  devicesReturnedAt DATETIME
);

-- Aktiv, kein Offboarding
INSERT INTO employee_entity (id, firstName, lastName, street, houseNumber, postalCode, city, country, iban, bic, accountHolder,
  terminationDate, terminationReason, terminationStatus, systemPermissionsRevokedAt, devicesReturnedAt)
VALUES (1, 'Anna', 'Schmidt', 'Hauptstrasse', '12A', '10115', 'Berlin', 'Deutschland', 'DE44500105175407324931', 'BELADEBEXXX', 'Anna Schmidt',
  NULL, NULL, NULL, NULL, NULL);

-- Kuendigung eingereicht, Offboarding laeuft
INSERT INTO employee_entity (id, firstName, lastName, street, houseNumber, postalCode, city, country, iban, bic, accountHolder,
  terminationDate, terminationReason, terminationStatus, systemPermissionsRevokedAt, devicesReturnedAt)
VALUES (2, 'Markus', 'Weber', 'Bahnhofstrasse', '5', '80331', 'Muenchen', 'Deutschland', 'DE21500500009876543210', 'COBADEFFXXX', 'Markus Weber',
  '2026-03-31', 'Wechsel des Arbeitgebers', 'IN_PROGRESS', NULL, NULL);

-- Offboarding abgeschlossen (Berechtigungen & Devices erledigt)
INSERT INTO employee_entity (id, firstName, lastName, street, houseNumber, postalCode, city, country, iban, bic, accountHolder,
  terminationDate, terminationReason, terminationStatus, systemPermissionsRevokedAt, devicesReturnedAt)
VALUES (3, 'Sofia', 'Keller', 'Ringstrasse', '22', '50667', 'Koeln', 'Deutschland', 'DE12500105170648489890', 'INGDDEFFXXX', 'Sofia Keller',
  '2025-12-31', 'Umzug ins Ausland', 'COMPLETED', '2025-12-15 10:30:00', '2025-12-20 16:45:00');