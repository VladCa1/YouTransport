package com.trans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trans.models.offer.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long>{

}
