package com.trans.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trans.models.offer.GoodsOffer;
import com.trans.models.offer.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long>{

	@Query("select off from GoodsOffer off where off.providerAgent.token = :token")
	List<GoodsOffer> findAllGoodsByToken(String token);

	@Query("select off.providerAgent.token from GoodsOffer off where off.id = :id")
	String getTokenForId(Long id);

	@Query("select off from GoodsOffer off")
	List<GoodsOffer> findAllGoods();

}
