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

-- Active permission
INSERT INTO permission_entity (id, employee_number, system_name, role_name, valid_from, valid_until, revoked)
VALUES ('11111111-1111-1111-1111-111111111111', 1, 'HR-APP', 'ADMIN', '2025-01-01', '2026-12-31', FALSE);

-- Expired permission
INSERT INTO permission_entity (id, employee_number, system_name, role_name, valid_from, valid_until, revoked)
VALUES ('22222222-2222-2222-2222-222222222222', 2, 'DEVICE-APP', 'USER', '2023-01-01', '2024-12-31', FALSE);

-- Revoked permission
INSERT INTO permission_entity (id, employee_number, system_name, role_name, valid_from, valid_until, revoked)
VALUES ('33333333-3333-3333-3333-333333333333', 3, 'AUTH-PORTAL', 'AUDITOR', '2024-06-01', '2026-06-01', TRUE);CREATE TABLE device_entity (
  id int NOT NULL,
  PRIMARY KEY (id)
);