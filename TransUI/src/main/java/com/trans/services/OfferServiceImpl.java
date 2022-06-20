package com.trans.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.vaadin.flow.router.QueryParameters;
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
	public boolean saveUpdateGoods(GoodsFormResultEntryDTO goodsFormResult) {
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

	public boolean checkUserCanSeeOffer(String parameter, QueryParameters queryParameters) throws Exception {

		WebClient client = WebClient.create();

		String uri = "localhost:9061/offer/checkOffer/" + parameter;

		ResponseEntity<Boolean> response = client.get()
				.uri(uriBuilder -> uriBuilder.path(uri)
						.queryParam("type", queryParameters.getParameters().get("type").get(0))
						.build())
				.header("Authorization", SecurityUtils.getUserToken())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.toEntity(Boolean.class)
				.block();
		if(response.getBody() != null) {
			Boolean result = response.getBody();
			return result;
		}else{
			throw new Exception("Can not get response");
		}

	}

	@Override
	public GoodsFormResultEntryDTO getGoodsOffer(Long id) throws Exception {
		WebClient client = WebClient.create();

		String uri = "localhost:9061/offer/goods/" + id.toString();

		ResponseEntity<GoodsFormResultEntryDTO> response = client.get()
				.uri(uriBuilder -> uriBuilder.path(uri)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.toEntity(GoodsFormResultEntryDTO.class)
				.block();
		if(response.getBody() != null) {
			return response.getBody();
		}else{
			throw new Exception("Can not get response");
		}
	}

	// TRANSPORT OFFERS

	@Override
	public TransportFormResultDTO getTransportOffer(Long id) throws Exception {
		WebClient client = WebClient.create();

		String uri = "localhost:9061/offer/transport/" + id.toString();

		ResponseEntity<TransportFormResultDTO> response = client.get()
				.uri(uriBuilder -> uriBuilder.path(uri)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.toEntity(TransportFormResultDTO.class)
				.block();
		if(response.getBody() != null) {
			return response.getBody();
		}else{
			throw new Exception("Can not get response");
		}
	}

	@Override
	public boolean deleteTransportOffer(TransportFormResultDTO offer){
		WebClient client = WebClient.create();

		String uri = "localhost:9061/offer/transport/delete/" + offer.getId();

		ResponseEntity<Boolean> response = client.delete()
				.uri(uri)
				.header("Authorization", SecurityUtils.getUserToken())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.toEntity(Boolean.class)
				.block();
		return response.getBody().booleanValue();
	}

	@Override
	public boolean saveUpdateTransport(TransportFormResultDTO offer){
		WebClient client = WebClient.create();

		URI uri = null;
		try {
			uri = new URI("localhost:9061/offer/transport/saveUpdate");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		ResponseEntity<Boolean> response = client.post()
				.uri(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", SecurityUtils.getUserToken())
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(offer), TransportFormResultDTO.class)
				.retrieve()
				.toEntity(Boolean.class)
				.block();
		return response.getBody().booleanValue();
	}

	@Override
	public List<TransportFormResultDTO> getAllTransportOffers() {
		WebClient client = WebClient.create();

		URI uri = null;
		try {
			uri = new URI("localhost:9061/offer/findAll/transport");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		ResponseEntity<TransportFormResultDTO[]> response = client.get()
				.uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.toEntity(TransportFormResultDTO[].class)
				.block();
		return Arrays.asList(response.getBody());
	}

	@Override
	public List<TransportFormResultDTO> getTransportOfferForUser() {
		WebClient client = WebClient.create();

		URI uri = null;
		try {
			uri = new URI("localhost:9061/offer/findAllUser/transport/");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		ResponseEntity<TransportFormResultDTO[]> response = client.get()
				.uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", SecurityUtils.getUserToken())
				.retrieve()
				.toEntity(TransportFormResultDTO[].class)
				.block();
		return Arrays.asList(response.getBody());
	}

}
