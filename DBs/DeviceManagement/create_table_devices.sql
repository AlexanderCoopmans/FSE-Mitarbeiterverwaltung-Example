-- Drop and recreate demo table for local testing
DROP TABLE IF EXISTS device_entity;

CREATE TABLE device_entity (
  id INT PRIMARY KEY AUTO_INCREMENT,
  device_type VARCHAR(50) NOT NULL,
  manufacturer VARCHAR(100) NOT NULL,
  designation VARCHAR(150) NOT NULL,
  assignment_id VARCHAR(100),
  employee_number INT,
  assignment_start DATE,
  assignment_end DATE
);


-- Geräte für Anna Schmidt (Software Engineer, Backend)
INSERT INTO device_entity (id, device_type, manufacturer, designation, assignment_id, employee_number, assignment_start, assignment_end)
VALUES 
(101, 'NOTEBOOK', 'Apple', 'MacBook Pro 14" (M4 Pro, 32GB RAM, 1TB SSD)', 'AS-BER-2024-01', 1, '2024-04-01', NULL);

-- Geräte für Markus Weber (Product Manager)
INSERT INTO device_entity (id, device_type, manufacturer, designation, assignment_id, employee_number, assignment_start, assignment_end)
VALUES 
(201, 'NOTEBOOK', 'Microsoft', 'Surface Laptop 7 (Snapdragon X Elite, 32GB RAM)', 'MW-MUC-2023-01', 2, '2023-10-01', NULL),
(202, 'NOTEBOOK', 'Apple', 'iPad Pro 13" (M4, 512GB) inkl. Magic Keyboard', 'MW-MUC-2023-02', 2, '2023-10-15', NULL);

-- Geräte für Lukas Fischer (Data Engineer)
INSERT INTO device_entity (id, device_type, manufacturer, designation, assignment_id, employee_number, assignment_start, assignment_end)
VALUES 
(401, 'NOTEBOOK', 'Dell', 'Precision 5690 (Ultra 7, 64GB RAM, RTX 2000 Ada)', 'LF-HAM-2023-01', 4, '2023-06-01', '2026-06-01'),
(402, 'MONITOR', 'LG', '38" UltraWide Curved (38WR85QC-W)', 'LF-HAM-2023-02', 4, '2023-06-05', '2026-06-01');

-- Geräte für Mara Nguyen (HR Specialist, Vertrag beendet)
INSERT INTO device_entity (id, device_type, manufacturer, designation, assignment_id, employee_number, assignment_start, assignment_end)
VALUES 
(501, 'NOTEBOOK', 'HP', 'EliteBook 840 G9 (i5, 16GB RAM, 512GB SSD)', 'MN-FRA-2022-01', 5, '2022-01-15', '2024-11-30'),
(502, 'SMARTPHONE', 'Samsung', 'Galaxy S22 128GB Enterprise Edition', 'MN-FRA-2022-02', 5, '2022-01-15', '2024-11-30');

-- Unvergebene Geräte (Lagerbestand)
INSERT INTO device_entity (id, device_type, manufacturer, designation, assignment_id, employee_number, assignment_start, assignment_end)
VALUES 
(901, 'NOTEBOOK', 'Lenovo', 'ThinkPad T14 Gen 5 (Ryzen 7, 32GB RAM)', NULL, NULL, NULL, NULL),
(902, 'MONITOR', 'Dell', 'UltraSharp U2723QE (27", 4K, USB-C Hub)', NULL, NULL, NULL, NULL);