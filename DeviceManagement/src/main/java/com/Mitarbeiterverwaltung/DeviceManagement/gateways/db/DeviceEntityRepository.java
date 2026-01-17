package com.Mitarbeiterverwaltung.DeviceManagement.gateways.db;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceEntityRepository extends JpaRepository<DeviceEntity, Integer> {
    List<DeviceEntity> findByAssignmentEndBetween(LocalDateTime start, LocalDateTime end);
}
