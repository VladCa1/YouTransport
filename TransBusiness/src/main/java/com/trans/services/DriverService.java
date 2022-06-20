package com.trans.services;

import com.trans.models.transport.Driver;
import com.trans.serviceInterface.models.DriverResultEntryDTO;

import java.util.List;

public interface DriverService {
    Boolean saveDriver(DriverResultEntryDTO driver, String token);

    List<DriverResultEntryDTO> getAllDriversForUser(String token);

    Boolean deleteDriver(Long id, String token);

    Driver getDriverForUser(String token, Long driverId);
}
