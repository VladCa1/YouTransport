package com.trans.services;

import java.io.IOException;
import java.net.*;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.router.QueryParameters;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.cert.CertificateException;

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


	@Override
	public Double getDistance(String fromCity, String fromCountry, String toCity, String toCountry) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, UnrecoverableKeyException, KeyManagementException {

		DistanceMatrixResponse response = getDirectionInfo(fromCity + " " + fromCountry, toCity + " " + toCountry);

		return Double.valueOf(response.getRows().get(0).getElements().get(0).getDistance().getValue())/1000;

	}

	@Override
	public DistanceMatrixResponse getDirectionInfo(String origins, String destinations) throws IOException, NoSuchAlgorithmException, KeyManagementException {
		String uri = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origins + "&destinations=" + destinations + "&API_KEY";
		RestTemplate restTemplate = new RestTemplate();

		DistanceMatrixResponse response = restTemplate.getForObject(uri, DistanceMatrixResponse.class);
		return response;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
