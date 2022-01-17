package com.trans.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trans.serviceInterface.models.GoodsFormResultDTO;
import com.trans.services.OfferService;

@RestController
@RequestMapping("/offer")
public class OfferController {
	
	@Autowired
	private OfferService offerService;
	
	@PostMapping("/saveUpdate/goods")
	public String saveUpdateGoods(@RequestBody GoodsFormResultDTO offer, @RequestHeader(value = "Authorization") String token) {
		if(offerService.saveUpdate(offer, token)) {
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
	
}
