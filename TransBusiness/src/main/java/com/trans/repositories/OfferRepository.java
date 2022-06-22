package com.trans.repositories;

import java.util.List;

import com.trans.models.offer.TransportOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trans.models.offer.GoodsOffer;
import com.trans.models.offer.Offer;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{

	@Query("select off from GoodsOffer off where off.providerAgent.token = :token")
	List<GoodsOffer> findAllGoodsByToken(String token);

	@Query("select off from TransportOffer off where off.providerAgent.token = :token")
	List<TransportOffer> findAllTransportByToken(String token);

	@Query("select off.providerAgent.token from GoodsOffer off where off.id = :id")
	String getTokenForIdGoods(Long id);

	@Query("select off.providerAgent.token from TransportOffer off where off.id = :id")
	String getTokenForIdTransport(Long id);

	@Query("select off from GoodsOffer off")
	List<GoodsOffer> findAllGoods();

	@Query("select off from TransportOffer off")
	List<TransportOffer> findAllTransport();

	@Query("select off from GoodsOffer off where off.id = :id")
	GoodsOffer getGoodsById(Long id);

	@Query("select off from TransportOffer off where off.id = :id")
	TransportOffer getTransportById(Long id);

}
