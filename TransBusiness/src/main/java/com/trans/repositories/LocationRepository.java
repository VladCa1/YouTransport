package com.trans.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trans.models.route.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

	@Query("select lc.country from Location lc")
	List<String> findAllCountries();
	
	@Query("select lc.city from Location lc where lc.country = :country")
	List<String> findAllCitiesForCountry(@Param("country") String country);
	
	Location findByCountryAndCity(String country, String city);
	
}
