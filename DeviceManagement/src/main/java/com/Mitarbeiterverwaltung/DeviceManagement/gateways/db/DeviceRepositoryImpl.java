package com.Mitarbeiterverwaltung.DeviceManagement.gateways.db;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.Device;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceId;
import com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary.DeviceRepository;

public class DeviceRepositoryImpl implements DeviceRepository {

	private DeviceEntityRepository deviceEntityRepository;

	public DeviceRepositoryImpl(DeviceEntityRepository deviceEntityRepository) {
		this.deviceEntityRepository = deviceEntityRepository;
	}

	public Optional<Device> findDeviceById(DeviceId deviceId) {

		Optional<DeviceEntity> deviceEntity = deviceEntityRepository.findById(deviceId.getId());
		Optional<Device> device = deviceEntity.map(DeviceEntity::toDevice);

		return device;
	}

	public List<Device> findDevicesDueForReturnInMonth(YearMonth month) {
		LocalDateTime startOfMonth = month.atDay(1).atStartOfDay();
		LocalDateTime endOfMonth = month.atEndOfMonth().atTime(LocalTime.MAX);
		List<DeviceEntity> entities = deviceEntityRepository.findByAssignmentEndBetween(startOfMonth, endOfMonth);

		return entities.stream()
				.map(DeviceEntity::toDevice)
				.collect(Collectors.toList());
	}

	@Override
	public void save(Device device) {
		DeviceEntity deviceEntity = DeviceEntity.fromDevice(device);
		deviceEntityRepository.save(deviceEntity);
	}

}
