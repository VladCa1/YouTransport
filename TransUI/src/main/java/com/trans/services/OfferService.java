package com.trans.services;

import java.util.List;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;

public interface OfferService {

	boolean saveUpdate(GoodsFormResultEntryDTO goodsFormResult);

	List<GoodsFormResultEntryDTO> getGoodsOfferForUser();

	boolean deleteOffer(GoodsFormResultEntryDTO offer);

	List<GoodsFormResultEntryDTO> getAllGoodsOffers();

}
