package com.trans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trans.models.offer.GoodsOffer;

@Repository
public interface GoodsOfferRepository extends JpaRepository<GoodsOffer, Long> {

	
}
