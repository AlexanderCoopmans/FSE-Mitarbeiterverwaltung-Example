package com.Mitarbeiterverwaltung.DeviceManagement.usecases.primary;

import java.time.YearMonth;
import java.util.List;

public interface DeviceManagementService {

    String deviceInformation(int id);

    List<String> assignmentsDueForReturn(YearMonth month);
}
