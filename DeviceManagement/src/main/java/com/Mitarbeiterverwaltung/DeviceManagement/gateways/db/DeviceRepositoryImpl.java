package com.Mitarbeiterverwaltung.DeviceManagement.gateways.db;

import java.time.LocalDate;
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

	@Override
	public Optional<Device> findDeviceById(DeviceId deviceId) {

		Optional<DeviceEntity> deviceEntity = deviceEntityRepository.findById(deviceId.getId());
		Optional<Device> device = deviceEntity.map(DeviceEntity::toDevice);

		return device;
	}

	@Override
	public List<Device> findAllDevices() {
		return deviceEntityRepository.findAll().stream()
				.map(DeviceEntity::toDevice)
				.collect(Collectors.toList());
	}

	@Override
	public List<Device> findDevicesDueForReturnInMonth(YearMonth month) {
		LocalDate startOfMonth = month.atDay(1);
		LocalDate endOfMonth = month.atEndOfMonth();
		List<DeviceEntity> entities = deviceEntityRepository.findByDateRange(startOfMonth, endOfMonth);

		return entities.stream()
				.map(DeviceEntity::toDevice)
				.collect(Collectors.toList());
	}

	@Override
	public List<Device> findDevicesAssignedToEmployee(String employeeNumber) {
		LocalDate today = LocalDate.now();
		return deviceEntityRepository.findActiveAssignmentsByEmployee(employeeNumber, today).stream()
				.map(DeviceEntity::toDevice)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Device> findByAssignmentId(String assignmentId) {
		DeviceEntity entity = deviceEntityRepository.findByAssignmentId(assignmentId);
		return Optional.ofNullable(entity).map(DeviceEntity::toDevice);
	}

	@Override
	public Device save(Device device) {
		DeviceEntity deviceEntity = DeviceEntity.fromDevice(device);
		DeviceEntity saved = deviceEntityRepository.save(deviceEntity);
		return saved.toDevice();
	}

	@Override
	public void delete(DeviceId deviceId) {
		deviceEntityRepository.deleteById(deviceId.getId());
	}

}
