package com.trans.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

import com.trans.repositories.LocationRepository;

@Service
@Transactional
public class RouteServiceImpl implements RouteService{
	
	@Autowired
	LocationRepository locationRepository;
	
	@Override
	public List<String> findAllCountries() {
		List<String> countries = locationRepository.findAllCountries();
		countries = countries.stream().distinct().collect(Collectors.toList());
		return countries;
	}

	@Override
	public List<String> findAllCitiesForCountry(String country) {
		return locationRepository.findAllCitiesForCountry(country);
	}

}
