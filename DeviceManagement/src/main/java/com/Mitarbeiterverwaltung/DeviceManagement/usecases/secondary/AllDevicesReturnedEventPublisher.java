package com.Mitarbeiterverwaltung.DeviceManagement.usecases.secondary;

import com.Mitarbeiterverwaltung.DeviceManagement.domain.events.AllDevicesReturnedEvent;

public interface AllDevicesReturnedEventPublisher {

    public String publishDomainEvent(AllDevicesReturnedEvent event);

}
