package com.trans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trans.models.goods.GoodsEntity;

public interface GoodsEntityRepository extends JpaRepository<GoodsEntity, Long>{

}
