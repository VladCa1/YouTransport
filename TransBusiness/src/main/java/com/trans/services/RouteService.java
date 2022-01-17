package com.trans.services;

import java.util.List;

public interface RouteService {

	List<String> findAllCountries();

	List<String> findAllCitiesForCountry(String country);
}
