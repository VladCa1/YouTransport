package com.trans.services;

import com.trans.serviceInterface.models.DriverResultEntryDTO;

import java.util.List;

public interface DriverService {
    boolean addDriver(DriverResultEntryDTO driver);

    List<DriverResultEntryDTO> getAllDriversForUser();

    boolean deleteDriver(Long id);
}
