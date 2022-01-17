package com.trans.controllers;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trans.services.RouteService;

@RestController
@RequestMapping("/route")
public class RouteController {

	@Autowired
	private RouteService routeService;
	
	@GetMapping("/findAllCountries")
    public List<String> findAllCountries() {
        return routeService.findAllCountries();
    }
	
	@GetMapping("/findAllCitiesForCountry")
	public List<String> findAllCitiesForCountry(String country){
		List<String> result = routeService.findAllCitiesForCountry(country);
		result.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		return result;
	}
	
}
