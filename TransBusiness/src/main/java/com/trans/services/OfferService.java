package com.trans.services;

import java.util.List;

import com.trans.serviceInterface.models.GoodsFormResultDTO;
import com.trans.serviceInterface.models.TransportFormResultDTO;

public interface OfferService {

	boolean saveUpdateGoods(GoodsFormResultDTO offer, String token);

	List<GoodsFormResultDTO> getAllGoodsForUser(String token);

	boolean deleteGoodsOffer(String token, Long id);

	List<GoodsFormResultDTO> getAllGoods();

	boolean checkUserOwnsOffer(String token, Long offerId, String type);

    GoodsFormResultDTO getGoodsOffer(Long id);

    String getGoodsTokenForId(Long id);

	boolean saveUpdateTransport(TransportFormResultDTO offer, String token);

    boolean deleteTransportOffer(String token, Long id);

	List<TransportFormResultDTO> getAllTransportForUser(String token);

	List<TransportFormResultDTO> getAllTransport();

    TransportFormResultDTO getTransportOffer(Long id);

    String getTransportTokenForId(Long id);
}
