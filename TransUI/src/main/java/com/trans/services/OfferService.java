package com.trans.services;

import java.util.List;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.vaadin.flow.router.QueryParameters;

public interface OfferService {

	boolean saveUpdateGoods(GoodsFormResultEntryDTO goodsFormResult);

	List<GoodsFormResultEntryDTO> getGoodsOfferForUser();

	boolean deleteOffer(GoodsFormResultEntryDTO offer);

	List<GoodsFormResultEntryDTO> getAllGoodsOffers();

    boolean checkUserCanSeeOffer(String parameter, QueryParameters queryParameters) throws Exception;

    GoodsFormResultEntryDTO getGoodsOffer(Long id) throws Exception;

	TransportFormResultDTO getTransportOffer(Long aLong) throws Exception;

	boolean deleteTransportOffer(TransportFormResultDTO offer);

	boolean saveUpdateTransport(TransportFormResultDTO offer);

	List<TransportFormResultDTO> getAllTransportOffers();

	List<TransportFormResultDTO> getTransportOfferForUser();
}
