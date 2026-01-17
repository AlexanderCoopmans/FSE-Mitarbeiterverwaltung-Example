package com.Mitarbeiterverwaltung.DeviceManagement.gateways.db;

import java.time.LocalDate;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.AssignmentId;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.Device;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceAssignment;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceId;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.DeviceType;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.EmployeeReference;
import com.Mitarbeiterverwaltung.DeviceManagement.domain.ValidityPeriod;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DeviceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String deviceType;
	private String manufacturer;
	private String designation;
	private String assignmentId;
	private String employeeNumber;
	private LocalDate assignmentStart;
	private LocalDate assignmentEnd;
	private LocalDate returnedOn;

	public DeviceEntity() {
	}

	public DeviceEntity(int id, String deviceType, String manufacturer, String designation, String assignmentId,
			String employeeNumber, LocalDate assignmentStart, LocalDate assignmentEnd, LocalDate returnedOn) {
		this.id = id;
		this.deviceType = deviceType;
		this.manufacturer = manufacturer;
		this.designation = designation;
		this.assignmentId = assignmentId;
		this.employeeNumber = employeeNumber;
		this.assignmentStart = assignmentStart;
		this.assignmentEnd = assignmentEnd;
		this.returnedOn = returnedOn;
	}

	public Device toDevice() {
		DeviceAssignment assignment = null;
		if (assignmentId != null) {
			assignment = new DeviceAssignment(new AssignmentId(assignmentId),
					new EmployeeReference(employeeNumber),
					new ValidityPeriod(assignmentStart, assignmentEnd));
		}
		return new Device(new DeviceId(this.id), DeviceType.valueOf(this.deviceType), this.manufacturer,
				this.designation, assignment);
	}

	public static DeviceEntity fromDevice(Device device) {
		DeviceAssignment currentAssignment = device.getCurrentAssignment();
		ValidityPeriod period = currentAssignment != null ? currentAssignment.getValidityPeriod() : null;
		return new DeviceEntity(device.getDeviceId().getId(),
				device.getDeviceType().name(),
				device.getManufacturer(),
				device.getDesignation(),
				currentAssignment != null ? currentAssignment.getAssignmentId().getId()
						: null,
				currentAssignment != null ? currentAssignment.getEmployee().getEmployeeNumber()
						: null,
				currentAssignment != null ? period.getStartDate() : null,
				currentAssignment != null ? period.getEndDate() : null,
				currentAssignment != null && currentAssignment.isReturned()
						? currentAssignment.getReturnedOn()
						: null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public LocalDate getAssignmentStart() {
		return assignmentStart;
	}

	public void setAssignmentStart(LocalDate assignmentStart) {
		this.assignmentStart = assignmentStart;
	}

	public LocalDate getAssignmentEnd() {
		return assignmentEnd;
	}

	public void setAssignmentEnd(LocalDate assignmentEnd) {
		this.assignmentEnd = assignmentEnd;
	}

	public LocalDate getReturnedOn() {
		return returnedOn;
	}

	public void setReturnedOn(LocalDate returnedOn) {
		this.returnedOn = returnedOn;
	}

}
