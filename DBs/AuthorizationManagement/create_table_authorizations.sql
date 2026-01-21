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
-- Berechtigungen für Anna Schmidt (Software Engineer, Backend)
INSERT INTO permission_entity (id, employee_number, system_name, role_name, valid_from, valid_until, revoked)
VALUES 
('550e8400-e29b-41d4-a716-446655440001', 1, 'GitHub', 'Backend-Developer-Write', '2024-04-01', '2099-12-31', FALSE),
('550e8400-e29b-41d4-a716-446655440002', 1, 'AWS Console', 'PowerUserAccess', '2024-04-01', '2099-12-31', FALSE),
('550e8400-e29b-41d4-a716-446655440003', 1, 'Jira', 'Developer-Role', '2024-04-01', '2099-12-31', FALSE);

-- Berechtigungen für Markus Weber (Product Manager)
INSERT INTO permission_entity (id, employee_number, system_name, role_name, valid_from, valid_until, revoked)
VALUES 
('550e8400-e29b-41d4-a716-446655440004', 2, 'Jira', 'Product-Owner', '2023-10-01', '2099-12-31', FALSE),
('550e8400-e29b-41d4-a716-446655440005', 2, 'Confluence', 'Space-Admin-Product', '2023-10-01', '2099-12-31', FALSE),
('550e8400-e29b-41d4-a716-446655440006', 2, 'Productboard', 'Editor', '2023-10-01', '2099-12-31', FALSE);

-- Berechtigungen für Lukas Fischer (Data Engineer)
-- Hinweis: valid_until ist hier auf das Vertragsende 2026-06-01 begrenzt
INSERT INTO permission_entity (id, employee_number, system_name, role_name, valid_from, valid_until, revoked)
VALUES 
('550e8400-e29b-41d4-a716-446655440007', 4, 'Snowflake', 'Data-Engineer-Admin', '2023-06-01', '2026-06-01', FALSE),
('550e8400-e29b-41d4-a716-446655440008', 4, 'Airflow', 'DAG-Manager', '2023-06-01', '2026-06-01', FALSE),
('550e8400-e29b-41d4-a716-446655440009', 4, 'GitHub', 'Data-Pipeline-Repo-Write', '2023-06-01', '2026-06-01', FALSE);