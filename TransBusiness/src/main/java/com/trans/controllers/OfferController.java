package com.trans.controllers;

import java.util.List;

import com.trans.models.offer.TransportOffer;
import com.trans.serviceInterface.models.TransportFormResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.trans.serviceInterface.models.GoodsFormResultDTO;
import com.trans.services.OfferService;

@RestController
@RequestMapping("/offer")
public class OfferController {
	
	@Autowired
	private OfferService offerService;
	
	@PostMapping("/saveUpdate/goods")
	public String saveUpdateGoods(@RequestBody GoodsFormResultDTO offer, @RequestHeader(value = "Authorization") String token) {
		if(offerService.saveUpdateGoods(offer, token)) {
			return "SaveUpdated";
		}else {
			return "Failed";
		}
	}
	
	@GetMapping("/findAllForUser/goods")
	public List<GoodsFormResultDTO> findAllGoodsForUser(@RequestHeader(value = "Authorization") String token){
		return offerService.getAllGoodsForUser(token);
	}
	
	@GetMapping("/findAll/goods")
	public List<GoodsFormResultDTO> findAllGoods(){
		return offerService.getAllGoods();
	}
	
	@DeleteMapping("/deleteGoodsOffer/{id}")
	public String deleteGoodsOffer(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {
		if(offerService.deleteGoodsOffer(token, id)) {
			return "Deleted";
		}else {
			return "Failed";
		}
	}

	@GetMapping("checkOffer/{id}")
	public Boolean checkIfUserOwnsOffer(@RequestHeader(value = "Authorization") String token, @PathVariable Long id, @RequestParam String type){
		if(offerService.checkUserOwnsOffer(token, id, type)){
			return true;
		}else{
			return false;
		}
	}

	@GetMapping("/goods/{id}")
	public GoodsFormResultDTO getGoodsOffer(@PathVariable Long id){
		return offerService.getGoodsOffer(id);
	}

	@GetMapping("/goods/token/{id}")
	public String getTokenForGoodsId(@PathVariable Long id){
		return offerService.getGoodsTokenForId(id);
	}

	@GetMapping("/transport/token/{id}")
	public String getTokenForTransportId(@PathVariable Long id){
		return offerService.getTransportTokenForId(id);
	}

	@GetMapping("/transport/{id}")
	public TransportFormResultDTO getTransportOffer(@PathVariable Long id){
		return offerService.getTransportOffer(id);
	}

	@PostMapping("/transport/saveUpdate")
	public boolean saveUpdateTransport(@RequestBody TransportFormResultDTO offer, @RequestHeader(value = "Authorization") String token){
		return offerService.saveUpdateTransport(offer, token);
	}

	@DeleteMapping("/transport/delete/{id}")
	public boolean deleteTransportOffer(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {
		return offerService.deleteTransportOffer(token, id);
	}

	@GetMapping("/findAllUser/transport/")
	public List<TransportFormResultDTO> findAllTransportForUser(@RequestHeader(value = "Authorization") String token) {
		return offerService.getAllTransportForUser(token);
	}

	@GetMapping("/findAll/transport")
	public List<TransportFormResultDTO> findAllTransport(){
		return offerService.getAllTransport();
	}


}
