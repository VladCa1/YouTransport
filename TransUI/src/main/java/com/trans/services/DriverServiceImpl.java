package com.trans.services;

import com.trans.serviceInterface.models.DriverResultEntryDTO;
import com.trans.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService{

    @Autowired
    WebClient maxSizeWebClient;

    @Override
    public boolean addDriver(DriverResultEntryDTO driver) {

        WebClient client = WebClient.create();

        String uri = "localhost:9061/driver/new";

        ResponseEntity<Boolean> response = client.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getUserToken())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(driver), DriverResultEntryDTO.class)
                .retrieve()
                .toEntity(Boolean.class)
                .block();
            return response.getBody().booleanValue();
    }

    @Override
    public List<DriverResultEntryDTO> getAllDriversForUser() {

        String uri = "localhost:9061/driver/findAllForUser";

        ResponseEntity<DriverResultEntryDTO[]> response = maxSizeWebClient.get()
                .uri(uri)
                .header("Authorization", SecurityUtils.getUserToken())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(DriverResultEntryDTO[].class)
                .block();

        List<DriverResultEntryDTO> result = null;
        if(response.getBody() != null) {
            result = Arrays.asList(response.getBody());
        }
        return result;
    }

    @Override
    public boolean deleteDriver(Long id) {
        WebClient client = WebClient.create();

        String uri = "localhost:9061/driver/delete/";

        ResponseEntity<Boolean> response = client.delete()
                .uri(uri + id)
                .header("Authorization", SecurityUtils.getUserToken())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Boolean.class)
                .block();

        return response.getBody().booleanValue();

    }
}
