package com.trans.controllers;

import com.trans.models.transport.Driver;
import com.trans.serviceInterface.models.DriverResultEntryDTO;
import com.trans.serviceInterface.models.GoodsFormResultDTO;
import com.trans.services.DriverService;
import com.trans.utils.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.persistence.Basic;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/new")
    public Boolean saveDriver(@RequestBody DriverResultEntryDTO driver, @RequestHeader(value = "Authorization") String token){
        return driverService.saveDriver(driver, token);
    }

    @GetMapping("/findAllForUser")
    public List<DriverResultEntryDTO> findAllDriversForUser(@RequestHeader(value = "Authorization") String token){
        return driverService.getAllDriversForUser(token);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteDriver(@RequestHeader(value = "Authorization") String token, @PathVariable Long id){
        return driverService.deleteDriver(id, token);
    }

}
