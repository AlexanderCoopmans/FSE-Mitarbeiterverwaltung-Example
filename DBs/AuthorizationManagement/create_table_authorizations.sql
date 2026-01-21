-- Drop and recreate demo table for local testing
DROP TABLE IF EXISTS permission_entity;

CREATE TABLE permission_entity (
  id VARCHAR(36) PRIMARY KEY,
  employee_number INT NOT NULL,
  system_name VARCHAR(100) NOT NULL,
  role_name VARCHAR(100) NOT NULL,
  valid_from DATE NOT NULL,
  valid_until DATE NOT NULL,
  revoked BOOLEAN NOT NULL DEFAULT FALSE
);

-- Beispiel-Berechtigungen 
-- 1 Anna Schmidt: aktiv
INSERT INTO permission_entity (id, employee_number, system_name, role_name, valid_from, valid_until, revoked)
VALUES ('11111111-1111-1111-1111-111111111111', 1, 'VSC', 'ENGINEER', '2024-04-01', '2027-03-31', FALSE);

-- 2 Markus Weber: aktiv
INSERT INTO permission_entity (id, employee_number, system_name, role_name, valid_from, valid_until, revoked)
VALUES ('22222222-2222-2222-2222-222222222222', 2, 'PRODUCT-HUB', 'PM', '2023-10-01', '2027-09-30', FALSE);

-- 3 Sofia Keller:
INSERT INTO permission_entity (id, employee_number, system_name, role_name, valid_from, valid_until, revoked)
VALUES ('33333333-3333-3333-3333-333333333333', 3, 'DESIGN-SUITE', 'DESIGNER', '2025-05-01', '2029-04-30', FALSE);

-- 4 Lukas Fischer: 
INSERT INTO permission_entity (id, employee_number, system_name, role_name, valid_from, valid_until, revoked)
VALUES ('44444444-4444-4444-4444-444444444444', 4, 'DATA-PLATFORM', 'ENGINEER', '2023-06-01', '2026-06-01', FALSE);

-- 5 Mara Nguyen:
INSERT INTO permission_entity (id, employee_number, system_name, role_name, valid_from, valid_until, revoked)
VALUES ('55555555-5555-5555-5555-555555555555', 5, 'HR-APP', 'EDITOR', '2022-01-15', '2030-11-30', TRUE);



