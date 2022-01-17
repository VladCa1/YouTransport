package com.trans.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.utils.SecurityUtils;

import reactor.core.publisher.Mono;

@Service
public class OfferServiceImpl implements OfferService{

	@Override
	public boolean saveUpdate(GoodsFormResultEntryDTO goodsFormResult) {
		WebClient client = WebClient.create();
	 	
    	URI uri = null;
		try {
			uri = new URI("localhost:9061/offer/saveUpdate/goods");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	
		ResponseEntity<String> response = client.post()
	    .uri(uri)
	    .contentType(MediaType.APPLICATION_JSON)
	    .header("Authorization", SecurityUtils.getUserToken())    
	    .accept(MediaType.APPLICATION_JSON)
	    .body(Mono.just(goodsFormResult), GoodsFormResultEntryDTO.class)
	    .retrieve()
	    .toEntity(String.class)
	    .block();
		if(response.getBody().equals("SaveUpdated")) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<GoodsFormResultEntryDTO> getGoodsOfferForUser() {
		WebClient client = WebClient.create();
	 	
    	String uri = "localhost:9061/offer/findAllForUser/goods";
	
		ResponseEntity<GoodsFormResultEntryDTO[]> response = client.get()
	    .uri(uri)
	    .header("Authorization", SecurityUtils.getUserToken())
	    .accept(MediaType.APPLICATION_JSON)
	    .retrieve()
	    .toEntity(GoodsFormResultEntryDTO[].class)
	    .block();
		List<GoodsFormResultEntryDTO> result = null;
		if(response.getBody() != null) {
			result = Arrays.asList(response.getBody());
		}
		return result;
	}

	@Override
	public boolean deleteOffer(GoodsFormResultEntryDTO offer) {
		WebClient client = WebClient.create();
	 	
    	String uri = "localhost:9061/offer/deleteGoodsOffer/" + offer.getId();
	
		ResponseEntity<String> response = client.delete()
	    .uri(uri)
	    .header("Authorization", SecurityUtils.getUserToken())
	    .accept(MediaType.APPLICATION_JSON)
	    .retrieve()
	    .toEntity(String.class)
	    .block();
		if(response.getBody() != null && response.getBody().equalsIgnoreCase("Deleted")) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<GoodsFormResultEntryDTO> getAllGoodsOffers() {
		WebClient client = WebClient.create();
	 	
    	String uri = "localhost:9061/offer/findAll/goods";
	
		ResponseEntity<GoodsFormResultEntryDTO[]> response = client.get()
	    .uri(uri)
	    .accept(MediaType.APPLICATION_JSON)
	    .retrieve()
	    .toEntity(GoodsFormResultEntryDTO[].class)
	    .block();
		List<GoodsFormResultEntryDTO> result = null;
		if(response.getBody() != null) {
			result = Arrays.asList(response.getBody());
		}
		return result;
	}

}
