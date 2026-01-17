-- Drop and recreate demo table for local testing
DROP TABLE IF EXISTS device_entity;

CREATE TABLE device_entity (
  id INT PRIMARY KEY,
  device_type VARCHAR(50) NOT NULL,
  manufacturer VARCHAR(100) NOT NULL,
  designation VARCHAR(150) NOT NULL,
  assignment_id VARCHAR(100),
  employee_number VARCHAR(50),
  assignment_start DATE,
  assignment_end DATE,
  returned_on DATE
);

-- Unassigned device
INSERT INTO device_entity (id, device_type, manufacturer, designation)
VALUES (1, 'NOTEBOOK', 'Dell', 'Latitude 7440');

-- Assigned device, not yet returned
INSERT INTO device_entity (id, device_type, manufacturer, designation, assignment_id, employee_number, assignment_start, assignment_end)
VALUES (2, 'SMARTPHONE', 'Apple', 'iPhone 14', 'A-1001', 'E1', '2025-01-01', '2026-12-31');

-- Assigned device, already returned
INSERT INTO device_entity (id, device_type, manufacturer, designation, assignment_id, employee_number, assignment_start, assignment_end, returned_on)
VALUES (3, 'TABLET', 'Samsung', 'Galaxy Tab S9', 'A-1002', 'E2', '2024-01-01', '2026-01-31', '2024-11-15');