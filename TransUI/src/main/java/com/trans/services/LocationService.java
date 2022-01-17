package com.trans.services;

import java.net.URISyntaxException;
import java.util.List;

public interface LocationService {

	List<String> findAllCountries() throws URISyntaxException;
	
	List<String> findAllCitiesForCountry(String country) throws URISyntaxException;
	
}
