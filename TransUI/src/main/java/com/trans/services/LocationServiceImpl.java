package com.trans.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trans.security.data.User;
import com.trans.serviceInterface.models.AgentDTO;

import reactor.core.publisher.Mono;

@Service
public class LocationServiceImpl implements LocationService{

	@Override
	public List<String> findAllCountries() throws URISyntaxException {
		WebClient client = WebClient.create();
 	
    	URI uri = new URI("localhost:9061/route/findAllCountries/");
	
		ResponseEntity<String[]> response = client.get()
	    .uri(uri)
	    .accept(MediaType.APPLICATION_JSON)
	    .retrieve()
	    .toEntity(String[].class)
	    .block();

		return Arrays.asList(response.getBody());
	}

	@Override
	public List<String> findAllCitiesForCountry(String country) throws URISyntaxException {
		WebClient client = WebClient.create();
	 	
    	String uri = "localhost:9061/route/findAllCitiesForCountry";
	
		ResponseEntity<String[]> response = client.get()
	    .uri(builder -> builder.path(uri).queryParam("country", country).build())
	    .accept(MediaType.APPLICATION_JSON)
	    .retrieve()
	    .toEntity(String[].class)
	    .block();
		
		return Arrays.asList(response.getBody());
	}

}
