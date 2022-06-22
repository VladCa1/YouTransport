package com.trans.services;

import com.trans.serviceInterface.models.DistanceMatrixResponse;

import java.security.KeyManagementException;
import java.security.cert.CertificateException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;

public interface LocationService {

	List<String> findAllCountries() throws URISyntaxException;
	
	List<String> findAllCitiesForCountry(String country) throws URISyntaxException;

	Double getDistance(String fromCity, String fromCountry, String toCity, String toCountry) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, UnrecoverableKeyException, KeyManagementException;

	DistanceMatrixResponse getDirectionInfo(String origins, String destinations) throws IOException, NoSuchAlgorithmException, KeyManagementException;
}
