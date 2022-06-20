package com.trans.services;

import com.trans.models.agent.FTPAgent;
import com.trans.models.transport.Driver;
import com.trans.repositories.AgentRepository;
import com.trans.repositories.DriverRepository;
import com.trans.serviceInterface.models.DriverResultEntryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService{

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    AgentRepository agentRepository;

    @Override
    public Boolean saveDriver(DriverResultEntryDTO driver, String token) {

        Driver toSave = new Driver();
        try {
            toSave.setImage(ImageIO.read(new ByteArrayInputStream(driver.getImage())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        toSave.setAge(driver.getAge());
        toSave.setNationality(driver.getNationality());
        toSave.setExperience(driver.getExperience());
        toSave.setFirstName(driver.getFirstName());
        toSave.setLastName(driver.getLastName());
        toSave.setAssigned(false);

        toSave.setToken(token);

        try{
            driverRepository.save(toSave);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<DriverResultEntryDTO> getAllDriversForUser(String token) {
        return mapToDTO(driverRepository.findAllByToken(token));
    }

    @Override
    public Boolean deleteDriver(Long id, String token) {
        if(driverRepository.getTokenById(id).equals(token)){
            driverRepository.deleteById(id);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Driver getDriverForUser(String token, Long id) {
        if(driverRepository.getTokenById(id).equals(token)){
            return driverRepository.findById(id).orElse(null);
        }else{
            return null;
        }
    }

    public static List<DriverResultEntryDTO> mapToDTO(List<Driver> allDriversForUser) {

        List<DriverResultEntryDTO> resultList = new ArrayList<>();
        for (Driver driver:
                allDriversForUser) {
            DriverResultEntryDTO driverDTO = new DriverResultEntryDTO();
            driverDTO.setAge(driver.getAge());
            driverDTO.setExperience(driver.getExperience());
            driverDTO.setNationality(driver.getNationality());
            driverDTO.setFirstName(driver.getFirstName());
            driverDTO.setLastName(driver.getLastName());
            driverDTO.setAssigned(driver.isAssigned());
            driverDTO.setId(driver.getId());
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(driver.getImage(), "PNG", out);
                byte[] bytes = out.toByteArray();
                driverDTO.setImage(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultList.add(driverDTO);
        }
        return resultList;
    }
}
