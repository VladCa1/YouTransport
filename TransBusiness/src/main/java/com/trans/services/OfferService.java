package com.trans.services;

import java.util.List;

import com.trans.serviceInterface.models.GoodsFormResultDTO;

public interface OfferService {

	boolean saveUpdate(GoodsFormResultDTO offer, String token);

	List<GoodsFormResultDTO> getAllGoodsForUser(String token);

	boolean deleteGoodsOffer(String token, Long id);

	List<GoodsFormResultDTO> getAllGoods();

}
