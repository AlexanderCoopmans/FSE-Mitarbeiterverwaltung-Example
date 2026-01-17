package com.Mitarbeiterverwaltung.DeviceManagement.gateways.db;

import java.util.Optional;

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

	@Override
	public void save(Device device) {
		DeviceEntity deviceEntity = DeviceEntity.fromDevice(device);
		deviceEntityRepository.save(deviceEntity);
	}

}
